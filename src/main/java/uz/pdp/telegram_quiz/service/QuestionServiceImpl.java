package uz.pdp.telegram_quiz.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PollAnswer;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPoll;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.Question;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.entity.User;
import uz.pdp.telegram_quiz.repo.QuestionRepository;
import uz.pdp.telegram_quiz.repo.QuestionsSetRepository;
import uz.pdp.telegram_quiz.service.interfaces.QuestionService;
import uz.pdp.telegram_quiz.service.interfaces.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final TelegramBot telegramBot;
    private final QuestionRepository questionRepository;
    private final QuestionsSetRepository questionsSetRepository;

    private static final Pattern QUESTION_PATTERN = Pattern.compile(
            "\\+{5,}\\s*\\n" +                        // 5 yoki undan ortiq '+' belgisi bilan ajratilgan
                    "(?<question>[^=]+?)\\n" +                // Savol matni
                    "(?<options>(?:={5,}\\s*[^\\n]+\\n?)+)",  // Variantlar
            Pattern.DOTALL
    );
    private final UserService userService;


    @Override
    public List<QuestionsSet> saveByDocument(Document document) {
        try {
            GetFile getFile = new GetFile(document.fileId());
            GetFileResponse execute = telegramBot.execute(getFile);
            File file = execute.file();

            String fileUrl = "https://api.telegram.org/file/bot" + telegramBot.getToken() + "/" + file.filePath();

            java.io.File localFile = java.io.File.createTempFile("telegram_bot_file", ".txt");
            localFile.deleteOnExit();

            try (InputStream inputStream = new URL(fileUrl).openStream()) {
                Files.copy(inputStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(localFile.toPath(), StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            return extractQuestions(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the document file.", e);
        }
    }

    @Override
    public void sendQuiz(User user) {
        QuestionsSet questionsSet = user.getQuestionsSet();
        List<Question> questions = questionsSet.getQuestions();
        int currentQuestionIndex = user.getCurrentQuestionIndex();

        Question question = questions.get(currentQuestionIndex);
        List<String> options = question.getOptions();
        int correctIndex = question.getCorrectOptionIndex();

        // Savolni Telegram botga yuborish
        SendPoll sendPoll = new SendPoll(
                user.getChatId(),
                currentQuestionIndex +1+". "+question.getQuestionText(),
                options.toArray(new String[0])
        );
        sendPoll.correctOptionId(correctIndex);
        sendPoll.isAnonymous(false);
        sendPoll.type("quiz");
        user.setCorrectAnswerIndex(correctIndex);
        user.setCurrentQuestionIndex(currentQuestionIndex +1);
        userService.updateUser(user);
        SendResponse execute = telegramBot.execute(sendPoll);

        System.out.println("Poll sent: " + execute);
        System.out.println("Question: " + question.getQuestionText());
        System.out.println("Options: " + options);
        System.out.println("Correct Option Index: " + correctIndex);
    }

    @Override
    public void handlePollAnswer(PollAnswer pollAnswer,User user) {
        System.out.println("Keldii");
    }


//    public List<QuestionsSet> extractQuestions(String document) {
//        List<QuestionsSet> questionsSets = new ArrayList<>();
//        List<Question> questions = new ArrayList<>();
//        Matcher matcher = QUESTION_PATTERN.matcher(document);
//        int numberOfQuestions = 0;
//
//        while (matcher.find()) {
//            Question question = new Question();
//            question.setQuestionText(matcher.group("question").trim());
//
//            String optionsText = matcher.group("options");
//            List<String> optionsList = optionsText == null ? new ArrayList<>() :
//                    Arrays.stream(optionsText.split("=====\\s*"))
//                            .filter(option -> !option.isBlank())
//                            .map(String::trim)
//                            .toList();
//
//            question.setOptions(optionsList);
//
//            String correctAnswer = matcher.group("correctAnswer");
//            int correctAnswerIndex = -1;
//
//            if (correctAnswer != null) {
//                correctAnswer = correctAnswer.trim();
//                for (int i = 0; i < optionsList.size(); i++) {
//                    if (optionsList.get(i).equalsIgnoreCase(correctAnswer)) {
//                        correctAnswerIndex = i;
//                        break;
//                    }
//                }
//            }
//
//            if (correctAnswerIndex == -1) {
//                System.err.println("Correct answer not found in options for question: " + question.getQuestionText());
//                System.err.println("Options: " + optionsList);
//                System.err.println("Correct answer: " + correctAnswer);
//            }
//
//            question.setCorrectOptionIndex(correctAnswerIndex);
//
//            Question savedQuestion = questionRepository.save(question);
//            questions.add(savedQuestion);
//            numberOfQuestions++;
//
//            if (numberOfQuestions == 30) {
//                QuestionsSet questionsSet = new QuestionsSet();
//                questionsSet.setQuestions(questions);
//                QuestionsSet savedSet = questionsSetRepository.save(questionsSet);
//                savedSet.setTitle(((questionsSets.size()) * 30 + 1) + " - " + ((questionsSets.size() + 1) * 30));
//                questionsSetRepository.save(savedSet);
//                questionsSets.add(savedSet);
//
//                questions = new ArrayList<>();
//                numberOfQuestions = 0;
//            }
//        }
//
//        if (!questions.isEmpty()) {
//            QuestionsSet questionsSet = new QuestionsSet();
//            questionsSet.setQuestions(questions);
//            QuestionsSet savedSet = questionsSetRepository.save(questionsSet);
//            savedSet.setTitle(((questionsSets.size()) * 30 + 1) + " - " + ((questionsSets.size()) * 30 + numberOfQuestions));
//            questionsSetRepository.save(savedSet);
//            questionsSets.add(savedSet);
//        }
//
//        return questionsSets;
//    }

    public List<QuestionsSet> extractQuestions(String document) {
        List<QuestionsSet> questionsSets = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        int numberOfQuestions = 0;

        Matcher matcher = QUESTION_PATTERN.matcher(document);

        while (matcher.find()) {
            Question question = new Question();

            // Savol matnini olish
            question.setQuestionText(matcher.group("question").trim());

            // Javob variantlarini olish
            String optionsText = matcher.group("options");
            List<String> optionsList = new ArrayList<>();
            int correctAnswerIndex = -1;

            if (optionsText != null && !optionsText.isEmpty()) {
                // Variantlarni `=====` bo'yicha ajratish
                String[] optionsArray = optionsText.split("=====\\s*");
                for (int i = 0; i < optionsArray.length; i++) {
                    String option = optionsArray[i].trim();
                    if (option.startsWith("#")) {
                        correctAnswerIndex = i;
                        option = option.substring(1).trim(); // To‘g‘ri javobdan '#' olib tashlanadi
                    }

                    option = option.length() > 100 ? option.substring(0, 100) : option;

                    if (!option.isBlank()) {
                        optionsList.add(option);
                    }
                }
            } else {
                System.err.println("No options found for question: " + question.getQuestionText());
            }

            question.setOptions(optionsList);
            question.setCorrectOptionIndex(correctAnswerIndex-1);

            if (correctAnswerIndex == -1) {
                System.err.println("Correct answer not found in options for question: " + question.getQuestionText());
            }

            Question savedQuestion = questionRepository.save(question);
            questions.add(savedQuestion);
            numberOfQuestions++;

            // Har 30 savoldan keyin yangi set yaratish
            if (numberOfQuestions == 30) {
                QuestionsSet questionsSet = new QuestionsSet();
                questionsSet.setQuestions(questions);
                QuestionsSet savedSet = questionsSetRepository.save(questionsSet);
                savedSet.setTitle(((questionsSets.size()) * 30 + 1) + " - " + ((questionsSets.size() + 1) * 30));
                questionsSetRepository.save(savedSet);
                questionsSets.add(savedSet);

                questions = new ArrayList<>();
                numberOfQuestions = 0;
            }
        }

        // Qolgan savollarni yakuniy setga qo'shish
        if (!questions.isEmpty()) {
            QuestionsSet questionsSet = new QuestionsSet();
            questionsSet.setQuestions(questions);
            QuestionsSet savedSet = questionsSetRepository.save(questionsSet);
            savedSet.setTitle(((questionsSets.size()) * 30 + 1) + " - " + ((questionsSets.size()) * 30 + numberOfQuestions));
            questionsSetRepository.save(savedSet);
            questionsSets.add(savedSet);
        }

        return questionsSets;
    }


}

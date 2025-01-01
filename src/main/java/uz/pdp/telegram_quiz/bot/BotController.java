package uz.pdp.telegram_quiz.bot;//package uz.pdp.telegram_quiz.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import uz.pdp.telegram_quiz.entity.Question;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.entity.User;
import uz.pdp.telegram_quiz.service.interfaces.QuestionService;
import uz.pdp.telegram_quiz.service.interfaces.QuestionsSetService;
import uz.pdp.telegram_quiz.service.interfaces.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class BotController {
    private final TelegramBot bot;
    private final BotMessage botMessage;
    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionsSetService questionsSetService;


    @Async
    @SneakyThrows
    public void handleUpdate(Update update) {
        Message message = update.message();

//        while (true){
//            SendMessage sendMessage = new SendMessage(1096665176L,"Tinchi nima gap");
//            SendMessage sendMessage1 = new SendMessage(1096665176L,"Tinchi nima gap");
//            SendResponse execute = bot.execute(sendMessage);
//            SendResponse execute1 = bot.execute(sendMessage1);
//            System.out.println("execute = " + execute);
//            System.out.println("execute1 = " + execute1);
//        }


        if (message != null) {
            String text = message.text();

            User user = userService.findByChatId(message);
            if (text != null) {
                if (text.equals("/start")) {
                    DeleteMessage deleteMessage = new DeleteMessage(update.message().chat().id(), update.message().messageId());
                    bot.execute(deleteMessage);

                    botMessage.askSetOfQuestions(user,message);
                }
            } else if (message.document() != null) {
                Long chatId = user.getChatId();
                if (chatId !=5033574248L) {
                    SendMessage sendMessage = new SendMessage(chatId,"Botga faqat admin file tashlay oladi❗❗❗");
                    bot.execute(sendMessage);
                    return;
                }
                Document document = message.document();
                List<QuestionsSet> questionsSets = questionService.saveByDocument(document);
                for (QuestionsSet questionsSet : questionsSets) {
                    SendMessage sendMessage = new SendMessage(user.getChatId(), questionsSet.getTitle() + " -> " + questionsSet.getQuestions().size());
                    bot.execute(sendMessage);
                }
                SendMessage sendMessage;
                if (questionsSets.isEmpty()) {
                    sendMessage = new SendMessage(user.getChatId(), "Saqlashda xatolik yuz berdi. Iltimos file ichidagi ma'lumotlarni tekshiring");
                } else {
                    sendMessage = new SendMessage(user.getChatId(), "Savollar muaffaqiyatli saqlandi");
                }
                bot.execute(sendMessage);
                System.out.println("Ketdi.....");
            }

        } else if (update.pollAnswer() != null) {
            PollAnswer pollAnswer = update.pollAnswer();
            Long id = pollAnswer.user().id();
            Integer[] optionIds = pollAnswer.optionIds();
            System.out.println("optionIds = " + Arrays.toString(optionIds));

            User user = userService.findByChatId(id);

            int correctAnswersCount = user.getCorrectAnswersCount();

            Integer optionId = optionIds[0];

            if(optionId==user.getCorrectAnswerIndex()) {
                correctAnswersCount = user.getCorrectAnswersCount()+1;
                user.setCorrectAnswersCount(correctAnswersCount);
                userService.updateUser(user);
            }



            if(user.getCurrentQuestionIndex() ==user.getQuestionsSet().getQuestions().size()) {
                bot.execute(new SendMessage(user.getChatId(), """
                        Savollar tugadi
                        
                        Siz ko'rsatkichingiz👇
                        To'g'ri javoblar✅: %s ta
                        Noto'g'ri javoblar❌: %s ta
                        
                        Test ni qaytadan ishlash uchun menu dan /start ni bosing
                        """.formatted(correctAnswersCount,user.getQuestionsSet().getQuestions().size()- correctAnswersCount)));

                return;
            }


            questionService.sendQuiz(user);
        } else if (update.callbackQuery() != null) {
            Message chatId = update.callbackQuery().message();
            User user = userService.findByChatId(chatId);
            questionsSetService.exceptSetOfQuestions(update,user);
        }
    }
}
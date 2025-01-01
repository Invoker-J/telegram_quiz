package uz.pdp.telegram_quiz.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.entity.User;
import uz.pdp.telegram_quiz.repo.QuestionsSetRepository;
import uz.pdp.telegram_quiz.service.interfaces.QuestionService;
import uz.pdp.telegram_quiz.service.interfaces.QuestionsSetService;
import uz.pdp.telegram_quiz.service.interfaces.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionsSetServiceImpl implements QuestionsSetService {

    private final QuestionsSetRepository questionsSetRepository;
    private final TelegramBot telegramBot;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    @Transactional
    public List<QuestionsSet> findAll() {
        return questionsSetRepository.findAll();
    }

    @Override
    public void exceptSetOfQuestions(Update update, User user) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long userChatId = user.getChatId();
        DeleteMessage deleteMessage = new DeleteMessage(
                userChatId,update.callbackQuery().maybeInaccessibleMessage().messageId()
        );
        telegramBot.execute(deleteMessage);
        String questionsSetsId = callbackQuery.data();

        Optional<QuestionsSet> optionalQuestionsSet =
                questionsSetRepository.findById(UUID.fromString(questionsSetsId));
        if (optionalQuestionsSet.isEmpty()) {
            SendMessage sendMessage =
                    new SendMessage(
                            userChatId, "Bunday To'plamdagi savollar topilmadi. Iltimos xatolik haqida admminga habar bering"
                    );
            telegramBot.execute(sendMessage);
            return;
        }
        QuestionsSet questionsSet = optionalQuestionsSet.get();
        user.setQuestionsSet(questionsSet);
        userService.updateUser(user);
        questionService.sendQuiz(user);
    }
}

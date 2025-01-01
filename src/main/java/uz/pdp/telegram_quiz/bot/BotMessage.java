package uz.pdp.telegram_quiz.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.telegram_quiz.entity.User;
import uz.pdp.telegram_quiz.enums.Status;
import uz.pdp.telegram_quiz.service.interfaces.UserService;

@Component
@RequiredArgsConstructor
public class BotMessage {
    private final UserService userService;
    private final BotUtil botUtil;
    private final TelegramBot telegramBot;

    public void askSetOfQuestions(User user, Message message) {
        SendMessage sendMessage = new SendMessage(user.getChatId(),
                "Hurmatli %s, Iltimos kerakli savollar to'plamni tanlang"
                        .formatted(user.getFullName())
        );


        user.setQuestionsSet(null);
        user.setCurrentQuestionIndex(0);
        user.setCorrectAnswersCount(0);
        user.setCorrectAnswerIndex(-1);

        user.setStatus(Status.CHOOSE_SET_OF_QUESTIONS);
        userService.updateUser(user);

        sendMessage.replyMarkup(botUtil.generateSetOfQuestionsButtons());
        telegramBot.execute(sendMessage);
    }
}

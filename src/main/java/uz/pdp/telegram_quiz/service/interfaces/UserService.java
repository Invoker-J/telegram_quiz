package uz.pdp.telegram_quiz.service.interfaces;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.User;

    @Service
    public interface UserService {
        User findByChatId(Message message);
        User findByChatId(Long chatId);

        User updateUser(User user);
    }

package uz.pdp.telegram_quiz.service.interfaces;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.entity.User;

import java.util.List;

@Service
public interface QuestionsSetService {
    List<QuestionsSet> findAll();


    void exceptSetOfQuestions(Update update, User user);
}

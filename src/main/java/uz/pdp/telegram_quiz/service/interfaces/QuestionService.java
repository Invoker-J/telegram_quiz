package uz.pdp.telegram_quiz.service.interfaces;

import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.PollAnswer;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.entity.User;

import java.util.List;

@Service
public interface QuestionService {
    List<QuestionsSet> saveByDocument(Document document);

    void sendQuiz(User user);

    void handlePollAnswer(PollAnswer pollAnswer,User user);
}

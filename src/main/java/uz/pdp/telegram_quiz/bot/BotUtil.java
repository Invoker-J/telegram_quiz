package uz.pdp.telegram_quiz.bot;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.telegram_quiz.db.QuizBot;
import uz.pdp.telegram_quiz.entity.Question;
import uz.pdp.telegram_quiz.entity.QuestionsSet;
import uz.pdp.telegram_quiz.service.interfaces.QuestionsSetService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BotUtil {
    private final QuestionsSetService questionsSetService;

    public Keyboard generateSetOfQuestionsButtons() {
        List<QuestionsSet> questionsSets = questionsSetService.findAll();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        for (QuestionsSet questionsSet : questionsSets) {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(questionsSet.getTitle()).callbackData(questionsSet.getId().toString()));
        }
        return inlineKeyboardMarkup;
    }
}

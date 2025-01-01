package uz.pdp.telegram_quiz.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyBot {
    private final TelegramBot telegramBot;
    private final BotController botController;
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(updates ->{
            for (Update update : updates) {
                if (update != null) {
                    botController.handleUpdate(update);
                }else {
                    System.out.println("Update is null");
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });


    }
}

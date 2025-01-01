package uz.pdp.telegram_quiz;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class TelegramQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramQuizApplication.class, args);
    }

    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot("7807856924:AAFbrW51Ciul5y4fJtuRit9Inx2-HjCpkQU");
//        return new TelegramBot("6460766008:AAFGN-i4fSYv2l5UBCGpskbsuzSiJ_Xl0WE"   );
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // Asosiy 20 ta parallel vazifa
        executor.setMaxPoolSize(50);  // Maksimal 50 ta parallel vazifa
        executor.setQueueCapacity(100); // Navbatdagi vazifalar sig'imi 100 ta
        executor.initialize();
        return executor;
    }

}
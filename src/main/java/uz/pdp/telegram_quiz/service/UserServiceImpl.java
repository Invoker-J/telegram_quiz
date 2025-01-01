package uz.pdp.telegram_quiz.service;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.User;
import uz.pdp.telegram_quiz.repo.UserRepository;
import uz.pdp.telegram_quiz.service.interfaces.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByChatId(Message message) {
        Optional<User> optionalUser = userRepository.findByChatId(message.chat().id());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        User user = User.builder()
                .firstName(message.from().firstName())
                .lastName(message.from().lastName())
                .chatId(message.chat().id())
                .username(message.from().username())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}

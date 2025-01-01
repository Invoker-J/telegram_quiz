package uz.pdp.telegram_quiz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.telegram_quiz.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByChatId(Long chatId);
}

package uz.pdp.telegram_quiz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.telegram_quiz.entity.Question;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}

package uz.pdp.telegram_quiz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.telegram_quiz.entity.QuestionsSet;

import java.util.UUID;

public interface QuestionsSetRepository extends JpaRepository<QuestionsSet, UUID> {
}

package uz.pdp.telegram_quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.telegram_quiz.enums.Status;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long chatId;
    private String username;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int currentQuestionIndex;
    private int correctAnswerIndex;
    private int correctAnswersCount;
    @OneToOne
    private QuestionsSet questionsSet;
    private LocalDateTime currentQuestionSentTime;


    public String getFullName() {
        String fullName = "";
        if (this.firstName == null) {
            fullName = this.lastName;
        } else if (this.lastName == null) {
            fullName = this.firstName;
        }else {
            fullName = this.firstName + " " + this.lastName;
        }
        return fullName;
    }
}

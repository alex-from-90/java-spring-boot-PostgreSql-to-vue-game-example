package ru.simulator.hr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questionTable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "question_text", columnDefinition = "TEXT")
    private String question;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "note" , columnDefinition = "TEXT")
    private String note;
}
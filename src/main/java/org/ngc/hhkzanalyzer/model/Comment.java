package org.ngc.hhkzanalyzer.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}

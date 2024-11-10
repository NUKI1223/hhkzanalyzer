package org.ngc.hhkzanalyzer.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;
}

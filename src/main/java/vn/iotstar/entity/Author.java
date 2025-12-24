package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "author")
@NamedQuery(name = "author.findAll", query = "SELECT a FROM Author a")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, unique = true)
    private int authorId;

    @Column(name = "author_name", columnDefinition = "VARCHAR(100)")
    private String authorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // Quan hệ với BookAuthor
    @OneToMany(mappedBy = "author")
    private Set<BookAuthor> bookAuthors;
}

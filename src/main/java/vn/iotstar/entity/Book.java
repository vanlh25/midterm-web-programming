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
@Table(name = "books")
@NamedQuery(name = "book.findAll", query = "SELECT b FROM Book b")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid", nullable = false, unique = true)
    private int bookId;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "title", columnDefinition = "VARCHAR(200)")
    private String title;

    @Column(name = "publisher", columnDefinition = "VARCHAR(100)")
    private String publisher;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "publish_date")
    private LocalDate publishDate;


    @Column(name = "cover_image", columnDefinition = "VARCHAR(100)")
    private String coverImage;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "book")
    private Set<BookAuthor> bookAuthors;

    @OneToMany(mappedBy = "book")
    private Set<Rating> ratings;
}

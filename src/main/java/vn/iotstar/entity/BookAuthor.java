package vn.iotstar.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book_author")
@IdClass(BookAuthor.BookAuthorId.class)
@NamedQuery(name = "bookAuthor.findAll", query = "SELECT ba FROM BookAuthor ba")
public class BookAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookAuthorId implements Serializable {
    	
		private static final long serialVersionUID = 1L;
		
		private Integer book;
        private Integer author;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BookAuthorId)) return false;
            BookAuthorId that = (BookAuthorId) o;
            return Objects.equals(book, that.book) && Objects.equals(author, that.author);
        }

        @Override
        public int hashCode() {
            return Objects.hash(book, author);
        }
    }
}

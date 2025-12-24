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
@Table(name = "rating")
@IdClass(Rating.RatingId.class)
@NamedQuery(name = "rating.findAll", query = "SELECT r FROM Rating r")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingId implements Serializable {
    	
		private static final long serialVersionUID = 1L;
		
		private Integer user;
        private Integer book;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RatingId)) return false;
            RatingId that = (RatingId) o;
            return Objects.equals(user, that.user) && Objects.equals(book, that.book);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, book);
        }
    }
}

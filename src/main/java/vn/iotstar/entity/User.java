package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
@NamedQuery(name = "user.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "fullname", columnDefinition = "VARCHAR(50)")
    private String fullname;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "passwd", nullable = false, columnDefinition = "VARCHAR(32)")
    private String passwd;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;


    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings;
}

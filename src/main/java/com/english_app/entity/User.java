package com.english_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "avatar", length = 200)
    private String avatar;

    @Column(name = "isUserLocked")
    private Boolean isUserLocked;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Userrole> userroles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Vocabulary> vocabularies = new LinkedHashSet<>();

}
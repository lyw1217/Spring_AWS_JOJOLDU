package com.kcl.example.springboot.domain.user;

import com.kcl.example.springboot.domain.posts.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)    // JPA 로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정, 기본적으로 int 로 된 숫자가 저장되나 그 값이 무슨 코드를 의미하는지 알 수 없으므로 STRING 으로 저장될 수 있도록 선언
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name       = name;
        this.email      = email;
        this.picture    = picture;
        this.role       = role;
    }

    public User update(String name, String picture) {
        this.name       = name;
        this.picture    = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

package com.kcl.example.springboot.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* Entity : JPA 의 어노테이션
* Getter, NoArgsConstructor : lombok 의 어노테이션
* - 롬복은 코드를 단순화시켜 주지만, 필수는 아님
* - 주요 어노테이션인 @Entity 를 클래스에 가깝게 두고 롬복 어노테이션을 그 위로 둠
* - 이후에 코틀린 등의 새 언어 전환으로 롬복이 더이상 필요 없을 경우 쉽게 삭제 가능
*/
@Getter             // 클래스 내 모든 필드의 Getter 메소드를 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 추가, public Posts() {} 와 같은 효과
@Entity             // 테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 (_) 으로 테이블 이름을 매칭 ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity {    // 실제 DB 테이블과 매칭될 클래스이며 보통 Entity 클래스라고 함

    @Id     // 해당 테이블의 PK 필드를 의미
    @GeneratedValue( strategy = GenerationType.IDENTITY)    // PK의 생성 규칙을 나타냄, 스프링부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment 가 됨
    private Long id;

    /*
    테이블의 칼럼을 나타내며, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됨
    사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
    문자열의 경우 VARCHAR(255) 가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 타입을 TEXT 로 변경하고 싶은 등의 경우에 사용
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title      = title ;
        this.content    = content ;
        this.author     = author ;
    }

    public void update(String title, String content) {
        this.title      = title;
        this.content    = content;
    }
}

package com.kcl.example.springboot.config.auth.dto;

import com.kcl.example.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
    왜 기존의 User 클래스를 쓰지 않고 새로 만들어서 쓰는가?
    User 클래스를 그대로 사용하면 직렬화를 구현하지 않았다는 의미의 에러가 발생한다.
    근데 왜 User 클래스에 직렬화 코드를 넣지 않을까?
    User 클래스가 엔티티 이기 때문이다.
    엔티티 클래스는 언제 다른 엔티티와 관계가 형성될 지 모른다.
    예를 들어 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
    그래서 직렬화 기능을 가진 Session Dto 를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
*/
@Getter
public class SessionUser implements Serializable {
    // 인증된 사용자 정보만 필요함
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name       = user.getName();
        this.email      = user.getEmail();
        this.picture    = user.getPicture();
    }
}

package com.kcl.example.springboot.web;

import com.kcl.example.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    // 테스트를 진행할 때 JUnit 에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행, 즉, 스프링 부트 테스트와 JUnit 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class, // @WebMvcTest : 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) })
public class HelloControllerTest {

    @Autowired  // 스프링이 관리하는 빈(Bean)을 주입
    private MockMvc mvc;    // 웹 API 를 테스트 할 때 사용, 스프링 MVC 테스트의 시작점, 이 클래스를 통해 API 테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform( get("/hello") )   // MockMvc 를 통해 /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk() )     // mvc.perform 의 결과를 검증, 응답의 Status 를 검증
                .andExpect(content().string(hello)
        );    // 본문의 내용(Content)을 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto").param("name"  , name )   // API 테스트 할 때 사용될 요청 파라미터 설정 (String 만 허용)
                                            .param("amount", String.valueOf(amount) ) )
                                            .andExpect( status().isOk() )
                /*
                    JSON 응답값을 필드별로 검증할 수 있는 메소드
                    $ 를 기준으로 필드명을 명시
                    name, amount 를 검증하니 아래와 같이 명시
                 */
                .andExpect( jsonPath( "$.name"  , is(name) ) )
                .andExpect( jsonPath( "$.amount", is(amount) )
        );
    }
}

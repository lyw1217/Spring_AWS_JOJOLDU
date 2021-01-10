package com.kcl.example.springboot.service.posts;

import com.kcl.example.springboot.domain.posts.Posts;
import com.kcl.example.springboot.domain.posts.PostsRepository;
import com.kcl.example.springboot.web.dto.PostsListResponseDto;
import com.kcl.example.springboot.web.dto.PostsResponseDto;
import com.kcl.example.springboot.web.dto.PostsSaveRequestDto;
import com.kcl.example.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // (readOnly = true) : 트랙잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선되기 때문에 조회 기능만 있는 서비스 메소드에서 사용하길 권장
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
        /*
            .map(PostsListResponseDto::new) 는 .map(posts -> new PostsListResponseDto(posts)) 와 같은 의미
            postsRepository 결과로 넘어온 Posts 의 Stream 을 map 을 통해 PostsListResponseDto 로 변환 -> List 로 반환하는 메소드
         */
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts); // JpaRepository 에서 이미 delete 메소드를 지원함 , 엔티티를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용하면 id 로 삭제할 수도 있음
    }
}

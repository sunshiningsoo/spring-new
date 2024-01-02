package com.example.jojolduprac.service.posts;

import com.example.jojolduprac.domain.posts.Posts;
import com.example.jojolduprac.domain.posts.PostsRepository;
import com.example.jojolduprac.web.dto.PostsResponseDto;
import com.example.jojolduprac.web.dto.PostsSaveRequestDto;
import com.example.jojolduprac.web.dto.PostsUpdateRequestDto;
import com.example.jojolduprac.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final이 붙어있는 필드값에 생성자를 롬복이 만들어 준다는 것임 -> 계속 필드값 변화할 때마다 생성자를 바꿔야 하는 수고가 적어짐
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
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다, id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new PostsResponseDto(entity);

    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하괴, 조회의 기능만 남겨두어 조회속도가 개선된다
    public List<PostsListResponseDto> findOrderDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다." + id));

        postsRepository.delete(posts); // jpa repository에서 delete 메소드 지원하고 있음

    }

}

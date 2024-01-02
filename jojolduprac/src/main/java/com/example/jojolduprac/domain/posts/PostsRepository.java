package com.example.jojolduprac.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // JpaRepository<Entity 클래스, pk 타입>을 상속 -> 기본적인 CRUD가 자동으로 생성됨

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // spring data jpa에서 제공하지 않는 메소드는 쿼리로 작성해라
    List<Posts> findAllDesc();

}

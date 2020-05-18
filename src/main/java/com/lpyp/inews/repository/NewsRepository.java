package com.lpyp.inews.repository;

import com.lpyp.inews.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface NewsRepository extends JpaRepository<News,Integer> {

    Page<News> findAllByTagId(Pageable pageable, int tagId);

    Page<News> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update news set click=click+1 where id=?1",nativeQuery = true)
    int saveClickById(int id);
}

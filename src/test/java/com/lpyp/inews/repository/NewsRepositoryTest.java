package com.lpyp.inews.repository;

import com.lpyp.inews.entity.News;

import com.mysql.cj.protocol.x.Notice;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
class NewsRepositoryTest {

    @Autowired
    NewsRepository newsR;

    @Test
    void findAll() {
        Sort sort=Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable= PageRequest.of(0,10);
        Page<News> all = newsR.findAll(pageable);
    }
}
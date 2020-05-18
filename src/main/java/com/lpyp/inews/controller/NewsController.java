package com.lpyp.inews.controller;

import com.alibaba.fastjson.JSON;
import com.lpyp.inews.entity.News;
import com.lpyp.inews.repository.NewsRepository;
import com.lpyp.inews.util.ResultRes;
import com.lpyp.inews.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RestController
public class NewsController {

    @Value("classpath:static/json/data.json")
    private Resource dataRes;

    @Autowired
    NewsRepository newsR;

    @GetMapping("/lunbotu")
    public ResultRes getLunbo(){
        return ResultUtil.success(newsR.findAllByTagId(PageRequest.of(0,3),10).getContent());
    }

    @GetMapping("/newscategory")
    public ResultRes getCategory(){
        try {
            String areaData =  IOUtils.toString(dataRes.getInputStream(), Charset.forName("UTF-8"));
            return ResultUtil.success(JSON.parse(areaData));
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.serverError();
        }
    }

    @GetMapping("/newsList")
    public ResultRes getNewsList(int page, int limit,int tagId){
        if (page<1||limit<1||tagId<0)
            return ResultUtil.queryError();
        Pageable pageable= PageRequest.of(page-1,limit,Sort.by(Sort.Direction.DESC,"addTime"));
        Page<News> all;
        if(tagId==99){
            all = newsR.findAll(pageable);
        }else
            all = newsR.findAllByTagId(pageable,tagId);
        return ResultUtil.success(all.getContent());
    }

    @GetMapping("/news")
    public ResultRes getNews(int id){
        if (id<0)
            return ResultUtil.queryError();
        News news = newsR.findById(id).get();
        return ResultUtil.success(news);
    }

    @GetMapping("/newsclick")
    public ResultRes clickNews(int id){
        if (id<1)
            return ResultUtil.queryError();
        newsR.saveClickById(id);
        News news;
        news=newsR.findById(id).get();
        if (news==null)
            return ResultUtil.serverError();
        return ResultUtil.success(news);
    }
}

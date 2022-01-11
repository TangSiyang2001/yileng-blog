package com.tsy.blog.controller;

import com.tsy.blog.common.annotation.Cache;
import com.tsy.blog.common.annotation.LogRecord;
import com.tsy.blog.common.annotation.ParamCheck;
import com.tsy.blog.service.ArticleService;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.ArticleParam;
import com.tsy.blog.vo.params.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/8
 * 文章
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Cache(expire = 30 * 1000)
    @PostMapping
    @LogRecord(module = "文章", operation = "获取文章列表")
    public Result listArticles(@RequestBody @ParamCheck PageParam pageParam) {
        return articleService.listArticles(pageParam);
    }

    @Cache
    @GetMapping("/hot")
    @LogRecord(module = "文章", operation = "获取最热文章")
    public Result listPopularArticles() {
        int limit = 5;
        return articleService.listPopularArticles(limit);
    }

    @Cache(expire = 30*1000)
    @GetMapping("/new")
    @LogRecord(module = "文章", operation = "获取最新文章")
    public Result listLastArticles() {
        int limit = 5;
        return articleService.listLastArticles(limit);
    }

    @Cache
    @GetMapping("/listArchives")
    @LogRecord(module = "文章", operation = "获取文章归档")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 这个加缓存会引起评论数和阅读数无法实时更新
     *
     * @param id 文章id
     * @return 消息模型
     */
    @GetMapping("/view/{id}")
    @LogRecord(module = "文章", operation = "获取文章视图")
    public Result findArticleById(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("/publish")
    @LogRecord(module = "文章", operation = "发布文章")
    public Result publishArticle(@RequestBody @ParamCheck ArticleParam articleParam) {
        return articleService.publishArticle(articleParam);
    }
}

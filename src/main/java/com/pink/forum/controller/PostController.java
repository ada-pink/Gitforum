package com.pink.forum.controller;

import com.pink.forum.entity.Post;
import com.pink.forum.message.Result;
import com.pink.forum.service.PostService;
import com.pink.forum.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @program Gitforum
 * @description 帖子接口
 * @author DengPengfei
 * @create 2021-12-30 09:23
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "铁汁管理")
public class PostController {
    final PostService postService;

    /**
     * @description 创建帖子
     */
    @ApiOperation("创建新帖")
    @PostMapping("/posts")
    public Result setPost(@RequestBody Post post) {
        post.setUser_id(ShiroUtils.getUser().getId());
        postService.insertSelective(post);
        return Result.ok(post);
    }

    @ApiOperation("删除帖子")
    @DeleteMapping("/posts/{id}")
    public Result deletePost(@PathVariable("id") int id){
        return postService.deleteByPrimaryKey(id);
    }

    @ApiOperation("修改帖子")
    @PutMapping("/posts/{id}")
    public Result updatePost(@PathVariable("id") int id, @RequestBody Post post){
        post.setId(id);
        return postService.updateByPrimaryKey(post);
    }

    @ApiOperation("查看所有帖子")
    @GetMapping("/posts")
    public Result allPost(@RequestParam("pageSize") String pageSize, @RequestParam("pageNum") String pageNum){
        Result result = postService.selectAll(Integer.parseInt(pageSize), Integer.parseInt(pageNum));

        result.setCode("200");
        result.setMsg("OK");
        return result;
    }

    @ApiOperation("查看指定用户的帖子")
    @GetMapping("/posts/{id}")
    public Result postAll(@RequestParam("pageSize") String pageSize, @RequestParam("pageNum") String pageNum, @RequestParam("postId") String postId){
        Result result = postService.selectIdAll(Integer.parseInt(pageSize), Integer.parseInt(pageNum), Integer.parseInt(postId));

        result.setCode("200");
        result.setMsg("OK");
        return result;
    }
}
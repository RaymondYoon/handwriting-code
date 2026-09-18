package com.example.practice.post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public Post createpost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @GetMapping
    public List<Post> findAll(){
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable Long id,
            @RequestBody Post post){
        return postService.update(
                id,
                post.title,
                post.content,
                post.author
        );
    }
}

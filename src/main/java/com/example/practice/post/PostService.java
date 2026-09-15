package com.example.practice.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class PostService {
    private final PostRepository postRepository;
    
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createPost (Post post){
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> findAll(){
        return postRepository.findAll();
    }
}

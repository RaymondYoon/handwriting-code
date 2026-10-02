package com.example.practice.post;

import com.example.practice.post.dto.PostCreateRequestDto;
import com.example.practice.post.dto.PostResponseDto;
import com.example.practice.post.dto.PostUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto requestDto) {
        Post post = new Post(
                requestDto.title(),
                requestDto.content(),
                requestDto.author()
        );
        Post savedPost = postRepository.save(post);
        return PostResponseDto.from(savedPost);
    }

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDto findById(Long id) {
        Post post = findPostById(id);
        return PostResponseDto.from(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostUpdateRequestDto requestDto) {
        Post post = findPostById(id);
        post.update(
                requestDto.title(),
                requestDto.content(),
                requestDto.author()
        );
        return PostResponseDto.from(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }
}

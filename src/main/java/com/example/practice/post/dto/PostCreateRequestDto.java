package com.example.practice.post.dto;

public record PostCreateRequestDto (
    String title,
    String content,
    String author
){}


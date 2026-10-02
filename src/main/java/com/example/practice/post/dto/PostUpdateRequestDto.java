package com.example.practice.post.dto;

public record PostUpdateRequestDto(
    String title,
    String content,
    String author
){}

package com.example.demo.model;

import lombok.Data;

@Data
public class URL {
    private long id;
    private String shortURL;
    private String originalURL;
}

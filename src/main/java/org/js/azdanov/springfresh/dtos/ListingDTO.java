package org.js.azdanov.springfresh.dtos;

import java.time.LocalDateTime;

public record ListingDTO(
    Integer id,
    String title,
    String body,
    boolean live,
    String author,
    String area,
    LocalDateTime createdAt) {}

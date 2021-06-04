package org.js.azdanov.springfresh.dtos;

import java.time.LocalDateTime;

public record ListingDTO(
    String title, String body, String author, String area, LocalDateTime createdAt) {}

package org.js.azdanov.springfresh.dtos;

import java.time.LocalDateTime;

public record VisitedListingDTO(
    ListingDTO listing, int visited, LocalDateTime createdAt, LocalDateTime updatedAt) {}

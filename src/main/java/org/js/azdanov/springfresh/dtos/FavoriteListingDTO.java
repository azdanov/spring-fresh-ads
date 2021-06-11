package org.js.azdanov.springfresh.dtos;

import java.time.LocalDateTime;

public record FavoriteListingDTO(ListingDTO listing, LocalDateTime createdAt) {}

package com.example.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public @ToString class CartDTO {
    Long userId;
    Long bookId;
    int quantity;
}

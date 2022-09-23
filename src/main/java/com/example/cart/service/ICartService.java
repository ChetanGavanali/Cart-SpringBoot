package com.example.cart.service;


import com.example.cart.dto.CartDTO;
import com.example.cart.model.Cart;

import java.util.List;

public interface ICartService {
    Cart addCartData(CartDTO cartDTO);

    List<Cart> allCartList();

    Cart getCartDetailsByCartId(Long cartId);

    List<Cart> getCartDetailsByUserId(Long userId);

    List<Cart> getCartDetailsByToken(String token);

    Cart editCartByCartId(Long cartId, CartDTO cartDTO);

    String deleteCartByCartId(Long userId, Long cartId);
}

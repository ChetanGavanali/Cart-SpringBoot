package com.example.cart.service;


import com.example.cart.dto.CartDTO;
import com.example.cart.exception.CartException;
import com.example.cart.model.Cart;
import com.example.cart.repository.CartRepo;
import com.example.cart.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service

public class CartService implements ICartService{


    @Autowired
    CartRepo cartRepo;
    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Cart addCartData(CartDTO cartDTO) {
        Object userDetails = restTemplate.getForObject("http://localhost:8085/user/get/"+cartDTO.getUserId(), Object.class);
        System.out.println(userDetails.toString());
        Object bookDetails = restTemplate.getForObject("http://localhost:8086/book/get/"+cartDTO.getBookId(), Object.class);
        System.out.println(bookDetails.toString());
        if (userDetails.equals(null) &&bookDetails.equals(null)) {
            throw new CartException(" userid and bookid is invalid");
        }
        Cart cartDetails = new Cart(cartDTO);
        cartRepo.save(cartDetails);
        return cartDetails;

    }

    @Override
    public List<Cart> allCartList() {
        List<Cart> cartList = cartRepo.findAll();
        if(cartList.isEmpty()){
            throw new CartException("No Items added in cart yet!!");
        }else
            return cartList;
    }

    @Override
    public Cart getCartDetailsByCartId(Long cartId) {
        Optional<Cart> cartDetails = cartRepo.findById(cartId);
        if(cartDetails.isPresent()){
            return cartDetails.get();
        }else
            throw new CartException("Cart ID does not exist: Invalid ID");
    }

    @Override
    public List<Cart> getCartDetailsByUserId(Long userId) {
        List<Cart> userCartList = cartRepo.getCartListWithUserId(userId);
        if(userCartList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return userCartList;    }

    @Override
    public List<Cart> getCartDetailsByToken(String token) {
        Long userId = tokenUtility.decodeToken(token);
        List<Cart> userCartList = cartRepo.getCartListWithUserId(userId);
        if(userCartList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return userCartList;    }

    @Override
    public Cart editCartByCartId(Long cartId, CartDTO cartDTO) {
        Object userDetails = restTemplate.getForObject("http://localhost:8085/user/get/"+cartDTO.getUserId(), Object.class);
        System.out.println(userDetails.toString());
        Object bookDetails = restTemplate.getForObject("http://localhost:8086/book/get/"+cartDTO.getBookId(), Object.class);
        System.out.println(bookDetails.toString());
        Cart editdata = cartRepo.findById(cartId).orElse(null);
        if (userDetails.equals(null)&&bookDetails.equals(null)&&editdata.equals(null)) {
            throw new CartException(" id is invalid");
        }else
            editdata.setCartId(cartDTO.getBookId());
        editdata.setUserId(cartDTO.getUserId());
        editdata.setQuantity(cartDTO.getQuantity());
        return cartRepo.save(editdata);
    }

    @Override
    public String deleteCartByCartId(Long userId, Long cartId) {
        Optional<Cart> cartDetails = cartRepo.findById(cartId);
        if(cartDetails.isPresent() && cartDetails.get().getUserId().equals(userId)){
            cartRepo.deleteByCartId(cartId);
            return "Deleted Cart ID: "+cartId;
        }else {
            throw new CartException("Cart Does not found: Invalid Cart ID or User does not exist.");
        }
    }
}

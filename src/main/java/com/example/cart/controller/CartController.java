package com.example.cart.controller;


import com.example.cart.dto.CartDTO;
import com.example.cart.dto.ResponseDTO;
import com.example.cart.model.Cart;
import com.example.cart.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ICartService cartService;
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> addCartDetails(@RequestBody CartDTO cartDTO){
        Cart cartDetails = cartService.addCartData(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details Added", cartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllCartDetails(){
        List<Cart> cartList = cartService.allCartList();
        ResponseDTO responseDTO = new ResponseDTO("Cart List, total count: "+cartList.size(), cartList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/data/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDataByID(@PathVariable Long cartId){
        Cart cartDetails = cartService.getCartDetailsByCartId(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details with Cart ID: "+cartId, cartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/UserCart/{userId}")
    public ResponseEntity<ResponseDTO> getCartDataByUserID(@PathVariable Long userId){
        List<Cart> userCartDetails = cartService.getCartDetailsByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details with User ID: "+userId, userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/UserCartToken/{token}")
    public ResponseEntity<ResponseDTO> getCartDataByToken(@PathVariable String token){
        List<Cart> userCartDetails = cartService.getCartDetailsByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details with Token: "+token, userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PutMapping("/update/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Long cartId, @RequestBody CartDTO cartDTO){
        Cart response = cartService.editCartByCartId(cartId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Cart Data with Cart ID: "+cartId, response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userId}/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable Long cartId, @PathVariable Long userId){
        String response = cartService.deleteCartByCartId(userId,cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart Deleted Successfully", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}

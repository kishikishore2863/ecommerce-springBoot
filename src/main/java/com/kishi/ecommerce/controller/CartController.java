package com.kishi.ecommerce.controller;


import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.CartItem;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.request.AddItemRequest;
import com.kishi.ecommerce.response.ApiResponse;
import com.kishi.ecommerce.service.CartItemService;
import com.kishi.ecommerce.service.CartService;
import com.kishi.ecommerce.service.ProductService;
import com.kishi.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")

public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart>  findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user =userService.findUserByJwtToken(jwt);
        Cart cart=cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Product product =productService.findProductById(req.getProductId());

        CartItem item =cartService.addCartItem(user,
                product,
                req.getSize(),
                req.getQuantity());
        ApiResponse res =new ApiResponse();
        res.setMessage("item added to cart successfully");

        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);


    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader ("Authorization")String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res =new ApiResponse();
        res.setMessage("Item Remove From ");
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);

        CartItem updatedCartItem =null;
        if(cartItem.getQuantity()>0){
            updatedCartItem=cartItemService.updatecartItem(user.getId(),cartItemId,cartItem);
        }
        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }


}

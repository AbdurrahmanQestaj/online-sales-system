package org.example.onlinesalessystem.services;

import org.example.onlinesalessystem.exceptions.InvalidQuantityException;
import org.example.onlinesalessystem.exceptions.ProductNotFoundException;
import org.example.onlinesalessystem.models.CartItem;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.models.dto.CartItemDto;
import org.example.onlinesalessystem.repository.CartRepository;
import org.example.onlinesalessystem.repository.ProductRepository;

import java.util.List;

public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService() {
        this.cartRepository = new CartRepository();
        this.productRepository = new ProductRepository();
    }

    public CartItem addToCart(CartItemDto cartItemDto) throws Exception {
        if (cartItemDto.getQuantity() <= 0) {
            throw new InvalidQuantityException("Sasia duhet te jete me e madhe se 0.");
        }

        Product product = productRepository.getById(cartItemDto.getProductId());

        if (product == null) {
            throw new ProductNotFoundException("Produkti nuk ekziston.");
        }

        if (cartItemDto.getQuantity() > product.getQuantity()) {
            throw new InvalidQuantityException("Nuk ka sasi te mjaftueshme ne stok.");
        }

        CartItem existingItem = cartRepository.getByUserAndProduct(
                cartItemDto.getUserId(),
                cartItemDto.getProductId()
        );

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + cartItemDto.getQuantity();

            if (newQuantity > product.getQuantity()) {
                throw new InvalidQuantityException("Sasia totale ne shporte kalon stokun ekzistues.");
            }

            existingItem.setQuantity(newQuantity);
            return cartRepository.update(existingItem);
        }

        CartItem cartItem = new CartItem(
                cartItemDto.getUserId(),
                cartItemDto.getProductId(),
                cartItemDto.getQuantity()
        );

        return cartRepository.create(cartItem);
    }

    public List<CartItem> getCartByUserId(int userId) throws Exception {
        return cartRepository.getByUserId(userId);
    }

    public boolean removeFromCart(int cartItemId) throws Exception {
        return cartRepository.delete(cartItemId);
    }

    public void clearCart(int userId) throws Exception {
        cartRepository.clearCartByUserId(userId);
    }
}
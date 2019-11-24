/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.cart;

import haanh.book.BookDTO;
import java.util.Map;

/**
 *
 * @author HaAnh
 */
public class CartUtils {
    
//    private Map<Integer, BookDTO> cart;
//
//    public CartUtils(Map<Integer, BookDTO> cart) {
//        if (cart != null) {
//            this.cart = cart;
//        } else {
//            this.cart = new HashMap<>();
//        }
//    }
//
//    public Map<Integer, BookDTO> getCart() {
//        return cart;
//    }
    
    public void updateItemInCart(Map<Integer, BookDTO> cart, BookDTO addedItem, int newQuantity) {
        int bookId = addedItem.getId();
        BookDTO itemInCart = cart.get(bookId);
        if (itemInCart == null) {
            itemInCart = new BookDTO(bookId, addedItem.getTitle(), newQuantity, addedItem.getPrice());
        } else {
            itemInCart.setQuantity(newQuantity);
        }
        cart.put(bookId, itemInCart);
    }

    public void addBookToCart(Map<Integer, BookDTO> cart, BookDTO addedItem, int addedQuantity) {
        int bookId = addedItem.getId();
        BookDTO itemInCart = cart.get(bookId);
        if (itemInCart == null) {
            itemInCart = new BookDTO(bookId, addedItem.getTitle(), addedQuantity, addedItem.getPrice());
        } else {
            int currentQuantity = itemInCart.getQuantity();
            int newQuantity = currentQuantity + addedQuantity;
            itemInCart.setQuantity(newQuantity);
        }
        cart.put(bookId, itemInCart);
    }
    
    public void removeBookFromCart(Map<Integer, BookDTO> cart, int bookId) {
        cart.remove(bookId);
    }
    
}

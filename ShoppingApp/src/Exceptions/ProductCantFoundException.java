package Exceptions;

public class ProductCantFoundException extends RuntimeException {
    public ProductCantFoundException(String message) {
        super(message);
    }
}

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart{

    List<Product> cart = new ArrayList<>();

    public void addToCart(Product product)
    {
        cart.add(product);
    }

    public void removeFromCart()
    {
        listCart();
        String targetName = InputUtils.readString("Enter name that you want to remove from cart?");
        boolean isRemoved = cart.removeIf(product -> product.name().equalsIgnoreCase(targetName));
        if (isRemoved)
            System.out.println(targetName + " is removed from cart");
    }

    public void listCart()
    {
        for (Product p : cart)
        {
            if (p != null) {
                System.out.println(p);
            }
        }
    }

    private double calculateTotal()
    {
        return cart.stream().mapToDouble(Product::price).sum();
    }

    public void payment(PaymentBehavior paymentMethod)
    {
        double total = calculateTotal();

        if (total <= 0)
            System.out.println("Cart is empty!.Cannot pay!");

        paymentMethod.processPayment(total);
    }
}

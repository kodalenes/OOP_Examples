package StoreManager;

import Exceptions.EmptyCartException;
import Payment.PaymentBehavior;
import Utils.InputUtils;

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
        if (cart.isEmpty())
        {
            System.out.println("Cart is empty!");
            return;
        }

        listCart();
        String targetName = InputUtils.readString("Enter name that you want to remove from cart?");
        boolean isRemoved = cart.removeIf(product -> product.getName().equalsIgnoreCase(targetName));
        if (isRemoved)
            System.out.println(targetName + " is removed from cart");
    }

    public void listCart()
    {
        if (cart.isEmpty())
        {
            System.out.println("Cart is empty!");
            return;
        }

        for (Product p : cart)
        {
            if (p != null) {
                System.out.println(p);
            }
        }
    }

    private double calculateTotal()
    {
        return cart.stream().mapToDouble(Product::getPrice).sum();
    }

    public void payment(PaymentBehavior paymentMethod) throws EmptyCartException
    {
        double total = calculateTotal();

        if (total <= 0)
            throw new EmptyCartException("Empty cart.Add product to cart!");

        //Calculate total discount
        double totalDiscount = 0.0;
        for (Product p : cart)
        {
            totalDiscount += p.getDiscountAmount();
        }

        double finalAmount = total - totalDiscount;

        if (totalDiscount > 0)
        {
            System.out.println("----------");
            System.out.printf("Total: %.2f TL%n" , total);
            System.out.printf("Discount Amount: %.2f TL%n" , totalDiscount);
            System.out.printf("Discounted Total: %.2f TL%n" , finalAmount);
            System.out.println("----------");
        }

        paymentMethod.processPayment(finalAmount);

        cart.clear();
    }
}

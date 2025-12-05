package StoreManager;

import Exceptions.EmptyCartException;
import Exceptions.ProductCantFoundException;
import Payment.PaymentBehavior;
import Products.Product;
import Utils.InputUtils;

import java.util.HashMap;
import java.util.Map;


public class ShoppingCart{

    HashMap<Product , Integer> cart = new HashMap<>();

    public void addToCart(Product product, int amount)
    {
        cart.put(product ,cart.getOrDefault(product ,0) + amount);
        System.out.println(product.getName() + " added to cart.Total amount:" + cart.get(product));
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
        Product targetProduct = null;
        try {
            targetProduct = findProductByName(targetName);
        } catch (ProductCantFoundException e) {
            System.out.println(e.getMessage());
        }

        if (targetProduct != null) {
            int removeAmount = InputUtils.readInt("How many " + targetName + " do you want to remove?");

            if (cart.get(targetProduct) > removeAmount)//urun adedi silinecekten fazlaysa adedi azalt
            {
                int amount = cart.get(targetProduct);
                amount = (cart.get(targetProduct) - removeAmount);
                cart.put(targetProduct , amount);
            } else if (cart.get(targetProduct) == removeAmount) {//urun adedi silinecekle ayniysa remove
                cart.remove(targetProduct);
            } else
                System.out.println("Invalid expression!");
            System.out.println(targetName + " is removed from cart.");
        }
    }

    public Product findProductByName(String targetName) throws ProductCantFoundException {
        for (Product p : cart.keySet())
        {
            if (p.getName().equalsIgnoreCase(targetName))
            {
                return p;
            }
        }

        throw new ProductCantFoundException("Product cannot found!");
    }


    public void listCart()
    {
        if (cart.isEmpty())
        {
            System.out.println("Cart is empty!");
            return;
        }

        for (Product p : cart.keySet())
        {
            if (p != null) {
                System.out.println(p + "Amount:" + cart.getOrDefault(p ,0));//urun bilgisi + adedi
            }
        }
    }

    private double calculateTotal()
    {
        //Mapteki tum urunlerin fiyatiyla sayisini carparak hepsioni toplar ve topla fiyati dondurur
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void payment(PaymentBehavior paymentMethod) throws EmptyCartException
    {
        boolean isSuccessful;
        double total = calculateTotal();

        if (total <= 0)
            throw new EmptyCartException("Empty cart.Add product to cart!");

        //Calculate total discount
        double totalDiscount = 0.0;
        for (Product p : cart.keySet())
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

        isSuccessful = paymentMethod.processPayment(finalAmount);
        if (isSuccessful)
        {
            cart.clear();
        }
    }
}

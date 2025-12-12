package StoreManager;

import Exceptions.EmptyCartException;
import Exceptions.InsufficientStockException;
import Exceptions.ProductCantFoundException;
import Payment.PaymentBehavior;
import Products.Product;
import Utils.InputUtils;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart{

    HashMap<Product,Integer> cart = new HashMap<>();

    /**
     *
     * @param product Product to add cart
     * @param amount  Products amount to add cart
     * @exception InsufficientStockException product's stock amount doesn't enough throw that
     */
    public void addToCart(Product product, int amount)
    {
        if (product.getStockAmount() - amount < 0)
            throw new InsufficientStockException
                    ("Insufficient stock! " + product.getName() + " remaining:" + product.getStockAmount());
        product.setStockAmount(product.getStockAmount() - amount);//updates stock amount
        cart.put(product ,cart.getOrDefault(product ,0) + amount);
        System.out.printf("%s added to cart. Total amount:%d . (Total of the product:%.2f TL) (Cart total:%.2f TL)%n"
                ,product.getName()
                ,cart.get(product)
                ,product.getPrice() * cart.get(product)
                ,calculateTotal());
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
                targetProduct.setStockAmount(targetProduct.getStockAmount() + removeAmount);
            } else if (cart.get(targetProduct) == removeAmount) {//urun adedi silinecekle ayniysa remove
                cart.remove(targetProduct);
                targetProduct.setStockAmount(targetProduct.getStockAmount() + removeAmount);
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

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-10s %-15s%n", "PRODUCT", "PRICE", "AMOUNT", "TOTAL");
        System.out.println("-------------------------------------------------------------------------");

        double subtotal = 0.0;
        for (Map.Entry<Product,Integer> entry : cart.entrySet())
        {
            if (entry.getKey() != null) {
                Product p = entry.getKey();
                int amount = entry.getValue();
                double lineTotal = p.getPrice() * amount;
                subtotal += lineTotal;

                System.out.printf("%-20s %-15s %-10d %-15s%n",
                        p.getName(),                            // Urun Adi
                        String.format("%.2f TL", p.getPrice()), // Birim Fiyat
                        amount,                                 // Adet
                        String.format("%.2f TL", lineTotal)     // Satir Toplami (Fiyat * Adet)
                );
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Subtotal: %.2f TL%n", subtotal);
    }

    private double calculateTotal()
    {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    /**
     *  Calculates cart total and apply discounts if payment is successful clears cart.
     * @param paymentMethod the method how payment will be happened
     * @throws EmptyCartException if shopping cart is empty throw that
     */
    public void payment(PaymentBehavior paymentMethod) throws EmptyCartException
    {
        boolean isSuccessful;
        double total = calculateTotal();

        if (total <= 0)
            throw new EmptyCartException("Empty cart.Add product to cart!");

        System.out.println("----Cart Summary----");
        listCart();
        System.out.println("--------------------");

        //Calculate total discount
        double totalDiscount = 0.0;
        for (Map.Entry<Product,Integer> entry : cart.entrySet())
        {
            totalDiscount += entry.getKey().getDiscountAmount() * entry.getValue();
        }

        double finalAmount = total - totalDiscount;

        if (totalDiscount > 0)
        {
            System.out.println("----------");
            System.out.printf("Total: %.2f TL%n" , total);
            System.out.printf("Discount Amount: %.2f TL%n" , totalDiscount);
            System.out.printf("Discounted Total: %.2f TL%n" , finalAmount);
            System.out.println("----------");
        }else
        {
            System.out.println("----------");
            System.out.printf("Total: %.2f TL%n" , total);
            System.out.println("----------");
        }

        isSuccessful = paymentMethod.processPayment(finalAmount);
        if (isSuccessful)
        {
            cart.clear();
        }
    }

    /**
     * If user trying to exit while cart isn't empty this method will restore stock amounts
     */
    public void restoreStock() {
        if (!cart.isEmpty())
        {
            for (Map.Entry<Product,Integer> entry : cart.entrySet())
            {
                Product p = entry.getKey();
                p.setStockAmount(p.getStockAmount() + entry.getValue());
            }
        }
    }
}

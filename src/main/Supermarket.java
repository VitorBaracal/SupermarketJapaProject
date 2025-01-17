package main;

import models.Product;
import utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Supermarket {

    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final Map<Product, Integer> cart = new HashMap<>();

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {

        System.out.println();
        System.out.println("================================================");
        System.out.println("==            Welcome to SuperMarket          ==");
        System.out.println("================================================");
        System.out.println("       ***** Please select an option *****      ");
        System.out.println("------------------------------------------------");
        System.out.println("|              Option 1 - Register              |");
        System.out.println("|               Option 2 - List                 |");
        System.out.println("|               Option 3 - Buy                  |");
        System.out.println("|               Option 4 - Cart                 |");
        System.out.println("|               Option 5 - Exit                 |");
        System.out.println("------------------------------------------------");
        System.out.println();

        try {
            System.out.print("+-> ");
            int option = Integer.parseInt(input.nextLine());

            switch (option) {
                case 1 -> registerProducts();
                case 2 -> listProducts();
                case 3 -> buyProducts();
                case 4 -> showCart();
                case 5 -> {
                    System.out.println("Thanks for the preference! Bye!");
                    System.out.println("THE SYSTEM WAS CLOSED");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Choose a valid option");
                    menu();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            menu();
        }
    }

    private static void registerProducts() {

        System.out.println("Product Name: ");
        System.out.print("+-> ");
        String name = input.nextLine();

        System.out.println("Product Price: ");
        System.out.print("+-> ");
        try {
            Double price = Double.parseDouble(input.nextLine());
            Product product = new Product(name, price);
            products.add(product);

            System.out.println(product.getProductName() + " has been registered successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please try again.");
        }

        menu();
    }

    private static void listProducts() {
        if (!products.isEmpty()) {
            System.out.println("===== Products List ===== \n");

            for (Product p : products) {
                System.out.println("+------------------------------------+");
                System.out.println("| Product Name: " + p.getProductName());
                System.out.println("| Product Price: " + Utils.doubleToString(p.getProductPrice()));
                System.out.println("| Product ID: " + p.getProductId());
                System.out.println("+------------------------------------+");
            }
        } else {
            System.out.println("No products registered!");
        }

        menu();
    }

    private static void buyProducts() {
        if (!products.isEmpty()) {

            System.out.println("====== Products Available ====== \n");

            for (Product p : products) {
                System.out.println("+------------------------------------+");
                System.out.println("| Product Name: " + p.getProductName());
                System.out.println("| Product Price: " + Utils.doubleToString(p.getProductPrice()));
                System.out.println("| Product ID: " + p.getProductId());
                System.out.println("+------------------------------------+");
            }

            System.out.println("Enter Product Code:");
            System.out.print("+-> ");
            try {
                int id = Integer.parseInt(input.nextLine());
                Product selectedProduct = products.stream()
                        .filter(p -> p.getProductId() == id)
                        .findFirst()
                        .orElse(null);

                if (selectedProduct != null) {
                    System.out.println("Enter Quantity:");
                    System.out.print("+-> ");
                    int quantity = Integer.parseInt(input.nextLine());

                    if (quantity > 0) {
                        cart.put(selectedProduct, cart.getOrDefault(selectedProduct, 0) + quantity);
                        System.out.println(quantity + " units of " + selectedProduct.getProductName() + " were added into the cart!");

                        System.out.println("What would you like to do next?");
                        System.out.println("1 - Finalize Purchase");
                        System.out.println("2 - Return to Menu");
                        System.out.print("+-> ");

                        int option = Integer.parseInt(input.nextLine());

                        if (option == 1) {
                            finalizePurchase();
                        } else {
                            menu();
                        }
                    } else {
                        System.out.println("Invalid quantity. Returning to menu.");
                        menu();
                    }
                } else {
                    System.out.println("The product was not found!");
                    menu();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid product code and quantity.");
                buyProducts();
            }
        } else {
            System.out.println("There are no registered products!");
            menu();
        }
    }

    private static void showCart() {
        System.out.println("====== Products in your cart ====== \n");
        if (!cart.isEmpty()) {
            cart.forEach((product, quantity) -> {
                System.out.println("+------------------------------------+");
                System.out.println("| Product Name: " + product.getProductName());
                System.out.println("| Product Price: " + Utils.doubleToString(product.getProductPrice()));
                System.out.println("| Quantity: " + quantity);
                System.out.println("+------------------------------------+");
            });

            System.out.println("What would you like to do next?");
            System.out.println("1 - Finalize Purchase");
            System.out.println("2 - Return to Menu");
            System.out.print("+-> ");

            try {
                int option = Integer.parseInt(input.nextLine());

                if (option == 1) {
                    finalizePurchase();
                } else {
                    menu();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Returning to menu.");
                menu();
            }
        } else {
            System.out.println("The cart is empty!");
            menu();
        }
    }

    private static void finalizePurchase() {
        if (!cart.isEmpty()) {
            double total = 0.0;
            System.out.println("====== Your products on the cart ====== \n");

            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                total += product.getProductPrice() * quantity;

                System.out.println("+------------------------------------+");
                System.out.println("| Product Name: " + product.getProductName());
                System.out.println("| Product Price: " + Utils.doubleToString(product.getProductPrice()));
                System.out.println("| Quantity: " + quantity);
                System.out.println("+------------------------------------+");
            }

            System.out.println("The total value of your purchase is: " + Utils.doubleToString(total));
            cart.clear();
            System.out.println("Thanks for the preference! Bye");
        } else {
            System.out.println("Your cart is empty!");
        }

        menu();
    }
}

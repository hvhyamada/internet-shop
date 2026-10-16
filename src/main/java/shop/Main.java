
package shop;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cart cart = new Cart();

    // Каталог товарiв
    private static final List<Product> catalog;

    static {
        Category electronics = new Category(1, "Електронiка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        catalog = List.of(
            new Product(1, "Ноутбук", 19999.99,
                "Високопродуктивний ноутбук для роботи та iгор",
                electronics),
            new Product(2, "Смартфон", 12999.50,
                "Потужний i стильний смартфон iз великим екраном",
                smartphones),
            new Product(3, "Навушники", 2499.00,
                "Якiснi навушники iз шумозаглушенням",
                accessories)
        );
    }

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> showCatalog();
                case 2 -> addToCart();
                case 3 -> System.out.println(cart);
                case 4 -> checkout();
                case 0 -> {
                    System.out.println(
                        "Дякуємо, що скористалися нашим магазином!"
                    );
                    return;
                }
                default -> System.out.println(
                    "Неправильний вибiр. Спробуйте ще раз."
                );
            }
        }
    }


    private static void printMenu() {
        System.out.println("\nГоловне меню:");
        System.out.println("1 - Переглянути каталог товарiв");
        System.out.println("2 - Додати товар до кошика");
        System.out.println("3 - Переглянути кошик");
        System.out.println("4 - Оформити замовлення");
        System.out.println("0 - Вихiд");
        System.out.print("Виберiть дiю: ");
    }

    private static void showCatalog() {
        System.out.println("--- Каталог товарiв ---");
        catalog.forEach(System.out::println);
    }

    private static void addToCart() {
        System.out.print(
            "Введiть ID товару, який потрiбно додати до кошика: "
        );

        int id = readInt();

        catalog.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .ifPresentOrElse(
                cart::addProduct,
                () -> System.out.println(
                    "Товар iз таким ID не знайдено."
                )
            );
    }

    private static void checkout() {
        if (cart.getProducts().isEmpty()) {
            System.out.println(
                "Кошик порожнiй. Додайте товари перед оформленням замовлення."
            );
            return;
        }

        Order order = new Order(cart);

        System.out.println("Оформлення замовлення:");
        System.out.println(order);

        cart.clear();
    }

    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Будь ласка, введiть число: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера введення

        return value;
    }
}

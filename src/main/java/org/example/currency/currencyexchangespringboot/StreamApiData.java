package org.example.currency.currencyexchangespringboot;

import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StreamApiData {

    public static class User {
        String name;
        String email;
        int age;

        public User(String name, String email, int age) {
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public int getAge() {
            return this.age;
        }

        public String getEmail() {
            return this.email;
        }
    }

    public enum Status {
        NEW, PROCESSING, COMPLETED, CANCELLED
    }

    public static class Order {
        int id;
        Status status;
        BigDecimal total;

        public Order(int id, Status status, BigDecimal total) {
            this.id = id;
            this.status = status;
            this.total = total;
        }

        public BigDecimal getTotal() {
            return this.total;
        }

        public Status getStatus() {
            return this.status;
        }
    }

    public static class Product {
        int id;
        String name;
        BigDecimal price;

        public Product(int id, String name, BigDecimal price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public BigDecimal getPrice() {
            return this.price;
        }
    }

    public static class ProductDTO {
        int id;
        String name;

        public ProductDTO(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class Client {
        String name;
        boolean isActive;

        public Client(String name, boolean isActive) {
            this.name = name;
            this.isActive = isActive;
        }

        public String getName() {
           return this.name;
        }

        public boolean getIsActive() {
            return this.isActive;
        }
    }

    public static class Employee {
        String name;
        String department;

        public Employee(String name, String department) {
            this.name = name;
            this.department = department;
        }

        public String getName() {
            return this.name;
        }

        public String getDepartment() {
            return this.department;
        }
    }

    // Входные данные

    public static List<User> users = List.of(
            new User("Alice", "alice@example.com", 25),
            new User("Bob", "bob@company.com", 17),
            new User("Carol", "carol@example.org", 30)
    );

    public static List<Order> orders = List.of(
            new Order(1, Status.NEW, BigDecimal.valueOf(100)),
            new Order(2, Status.PROCESSING, BigDecimal.valueOf(200)),
            new Order(3, Status.COMPLETED, BigDecimal.valueOf(300)),
            new Order(4, Status.NEW, BigDecimal.valueOf(150))
    );

    public static List<Client> clients = List.of(
            new Client("Anna", false),
            new Client("Sergey", true),
            new Client("Elena", true)
    );

    public static List<Product> products = List.of(
            new Product(1, "Laptop", BigDecimal.valueOf(1500)),
            new Product(2, "Mouse", BigDecimal.valueOf(25)),
            new Product(3, "Monitor", BigDecimal.valueOf(300))
    );

    public static List<Employee> employees = List.of(
            new Employee("John", "IT"),
            new Employee("Mary", "Finance"),
            new Employee("Alex", "IT"),
            new Employee("Sophie", "Finance")
    );

    public static List<String> phrases = List.of(
            "Error occurred in module A",
            "Warning in module B",
            "error in module A"
    );

    public static List<List<Integer>> listOfLists = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
    );

    /*
     * В методе main необходимо:
     * 1. Отфильтровать пользователей старше 18 лет и собрать в список
     * 2. Сгруппировать заказы по статусу
     * 3. Подсчитать сумму всех заказов
     * 4. Найти первого активного клиента
     * 5. Есть список List<Product> с полями id, name, price. Нужно получить List<ProductDTO> с теми же данными, но только с полями id и name.
     * 6. Вытащить все уникальные домены email и отсортировать по алфавиту
     * 7. Найти товар с максимальной ценой
     * 8. Сгруппировать сотрудников по департаментам, а внутри — отсортировать по имени
     * 9. Посчитать, сколько раз встречается каждое слово во всех строках списка List<String> phrases (без учёта регистра)
     * 10. Преобразовать List<List<Integer>> listOfLists в один список List<Integer>
     * */
    public static void main(String[] args) {
        // 1. Отфильтровать пользователей старше 18 лет и собрать в список
        List<User> users = List.of(
                new User("Alice", "alice@example.com", 25),
                new User("Bob", "bob@company.com", 17),
                new User("Carol", "carol@example.org", 30)
        );

        List<User> filtered = users.stream()
                .filter(user -> user.getAge() > 18)
                .toList();

        // 2. Сгруппировать заказы по статусу
        List<Order> orders = List.of(
                new Order(1, Status.NEW, BigDecimal.valueOf(100)),
                new Order(2, Status.PROCESSING, BigDecimal.valueOf(200)),
                new Order(3, Status.COMPLETED, BigDecimal.valueOf(300)),
                new Order(4, Status.NEW, BigDecimal.valueOf(150))
        );

        Map<Status, List<Order>> groupedOrdersByStatus = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus));

        // 3. Подсчитать сумму всех заказов
        BigDecimal total = orders.stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //4. Найти первого активного клиента
        List<Client> clients = List.of(
                new Client("Anna", false),
                new Client("Sergey", true),
                new Client("Elena", true)
        );

        Optional<Client> firstActiveClient = clients.stream()
                .filter(Client::getIsActive)
                .findFirst();

        //5. Есть список List<Product> с полями id, name, price. Нужно получить List<ProductDTO> с теми же данными, но только с полями id и name.
        List<Product> products = List.of(
                new Product(1, "Laptop", BigDecimal.valueOf(1500)),
                new Product(2, "Mouse", BigDecimal.valueOf(25)),
                new Product(3, "Monitor", BigDecimal.valueOf(300))
        );

        List<ProductDTO> productDTOList = products.stream()
                .map(p -> new ProductDTO(p.getId(), p.getName()))
                .toList();

        //6. Вытащить все уникальные домены email и отсортировать по алфавиту
        List<String> emailList = users.stream()
                .map(User::getEmail)
                .filter(email -> email != null && email.contains("@"))
                .map(email -> email.substring(email.lastIndexOf('@') + 1))
                .distinct()
                .sorted()
                .toList();

        //7. Найти товар с максимальной ценой
        Optional<Product> maxPrice = products.stream()
                .max(Comparator.comparing(Product::getPrice));

        //8. Сгруппировать сотрудников по департаментам, а внутри — отсортировать по имени
        List<Employee> employees = List.of(
                new Employee("John", "IT"),
                new Employee("Mary", "Finance"),
                new Employee("Alex", "IT"),
                new Employee("Sophie", "Finance")
        );

        Map<String, List<Employee>> filteredEmloyees = employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        //9. Посчитать, сколько раз встречается каждое слово во всех строках списка List<String> phrases (без учёта регистра)
        List<String> phrases = List.of(
                "Error occurred in module A",
                "Warning in module B",
                "error in module A"
        );

        Map<String, Long> count = phrases.stream()
                .flatMap(phrase -> Arrays.stream(phrase.split("\\P{L}+")))
                .collect(Collectors.groupingBy(
                        word -> word,
                        Collectors.counting()
                ));

        //10. Преобразовать List<List<Integer>> listOfLists в один список List<Integer>
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8, 9)
        );

        List<Integer> singleList = listOfLists.stream()
                .flatMap(List::stream)
                .toList();
    }
}

# WebShop
Repo for the teamwork

# Database

## User
- id (BigInt)
- email (varchar(255))
- password (hash) (BigInt)
- OPTIONAL name (varchar(255)) 

## Product
- id (BigInt)
- name (varchar(255))
- price (int)
- OPTIONAL amount (int) 

## Buying
- id (BigInt)
- user_id (BigInt, fk)
- OPTIONAL date_and_time_of_buying (DATETIME) 

## Product_in_basket suggestion to rename Bought_product
- id (BigInt)
- buying_id (BigInt, fk)
- product_id (BigInt, fk)
- amount (int)

# Entities

## User
- id (Long)
- email (String)
- password hash(Long)


- User(String email, String password)
- User(Long id, String email, int password)
- getters & setters

## Product
- id (Long)
- name (String)
- price (int)


- Product(String name, int price)
- Product(Long id, String name, int price)
- getters

## BoughtProduct
- id (Long)
- user(User)
- products (Map<Product,Integer>)


- BoughtProduct(Long id, User user)
- void addProduct(Product product)
- void removeProduct(Product product)
- getters


# Repository layer

## UserRepository

- _void saveUser(User user)_
- _Optional<User> findUserByEmail(String email)_

## ProductRepository

- _void saveProduct(Product product)_
- _Optional<Product> findProductByName(String name)_

## BoughtProductRepository

- __saveBuying()__
- __findBuyingById()__

# Service

## BoughtProductService

- BoughtProductRepository basketRepository


- BoughtProductService(BoughtProductRepository basketRepository)

## ProductService

- ProductRepository productRepository


- ProductService(ProductRepository productRepository)
- void insertProduct(Product product)
- void insertMultipleProducts(List<Product> products)
- Optional<Product> findProductByName(String name)


## UserService

- UserRepository userRepository;
- UserValidator validator;


- UserService(UserRepository userRepository)
- void registerUser(String email, String password)
- boolean loginUser(String email, String password)


# Validators

## BoughtProductValidator

- void validateProductInBasket(Map<Product, Integer> products, Product product)

## UserValidator

- UserValidator(UserRepository userRepository)


- UserValidator(UserRepository userRepository)
- void validateRegistration(String email, String password)
- boolean checkIfUserExists(String email)


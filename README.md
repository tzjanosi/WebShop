# WebShop
Repo for the teamwork

# Database

## User
- id (BigInt)
- email (varchar(255))
- password (hash) (BigInt)

## Product
- id (BigInt)
- name (varchar(255))
- price (int)

## Buying
- id (BigInt)
- user_id (BigInt, fk)

## Product_in_basket
- id (BigInt)
- basket_id (BigInt, fk)
- product_id (BigInt, fk)
- amount (int)

# Entities

## User
- id (Long)
- email (String)
- password hash(Long)

## Product
- id (Long)
- name (String)
- price (int)

# Repository layer

## UserDAO

- saveUser()
- findUserByEmail()

## ProductDAO

- saveProduct()
- findProductByName()

## BuyingDAO

- saveBuying()
- findBuyingById()

## BasketDAO

- saveBasket()
- findProductsByBuyingId()

# Service

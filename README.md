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

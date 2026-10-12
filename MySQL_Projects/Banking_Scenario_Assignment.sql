create database Banking_Scenario;

use Banking_Scenario;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    city VARCHAR(50)
);

INSERT INTO customers (name, city) VALUES
('Janani','Chennai'),
('Arun','Coimbatore'),
('Priya','Madurai'),
('Karthik','Salem'),
('Ashwinth','Cuddaloer'),
('Sheela','Bangalore');

select * from customers;

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1,'Savings',50000),
(1,'Current',20000),
(2,'Savings',30000),
(3,'Savings',15000),
(4,'Current',40000),
(5,'Savings',60000);


select * from accounts;

-- Retrieve all accounts with balance greater than 20,000.

select * from accounts where balance>20000;

-- Find customers who live in Chennai.

select * from customers where city In ('chennai'); 

-- Display accounts with balance between 20,000 and 50,000.

select * from accounts where balance between 20000 And 50000; 

-- Find customers whose names start with 'J'.

select * from customers where name like 'j%';

-- Retrieve accounts of type 'Savings' or 'Current'.

select * from accounts where account_type in ('Savings','current');  

-- Display accounts that are not 'Savings'

select * from accounts where account_type not in ('Savings');

-- Find customers whose names contain the letter 'a'.

select * from customers where name like '%a%'; 

-- Retrieve accounts with balance less than or equal to 30,000

select * from accounts where balance <= 30000;

-- Find customers who are not from Madurai.

select * from customers where city not in ('Madurai'); 

-- Display accounts where balance is not between 10,000 and 40,000. 

select * from accounts where balance not between 10000 and 40000;

-- Retrieve customers whose names end with 'i'.

select * from customers where name like '%i';

-- Find accounts with balance equal to 50,000.

select * from accounts where balance = 50000;

-- Display customers whose city is either Chennai or Salem.

select * from customers where city = 'chennai' or city = 'salem'; 

-- Find accounts with balance greater than 10,000 and less than 40,000.

select * from accounts where balance > 10000 and balance < 40000;

-- Retrieve accounts where account type is not in ('Current').

select * from accounts where account_type not in ('current');

-- Display all accounts sorted by balance in descending order.  

select * from accounts 
order by balance desc;

-- List customers sorted alphabetically by name. 
select * from customers
order by name asc;

-- Display accounts sorted by account type and then by balance (descending). 

select * from accounts 
order by account_type asc , balance desc ;

-- Find the total balance of all accounts

select sum(balance) as tot_bal from accounts;

-- Calculate the average balance of accounts. 

select avg(balance) as avg_bal from accounts;

-- Find the maximum account balance. 

select Max(balance) as max_bal from accounts;

-- Find the minimum account balance.

select min(balance) as min_bal from accounts;

-- Count the total number of customers 

select count(*) as tot_count from customers;

-- Find total balance grouped by account type

select account_type , sum(balance) as tot_bal
from accounts
group by account_type;

-- Find average balance for each account type. 

select account_type,Round(avg(balance),2) avg_bal
from accounts
group by account_type;

-- Display account types having average balance greater than 20,000. 

select account_type , Round(avg(balance),2) as avg_balance
from accounts
group by account_type
having avg(balance)>20000;

-- Count number of accounts for each customer.

select customer_id , count(account_id) as count_acc
from  accounts
group by customer_id;

-- Display customers having more than one account. 

select customer_id , count(account_id) as count_acc
from  accounts
group by customer_id
having count(account_id)>1;

-- Retrieve customer names along with their account balances

select c.name , a.balance 
from customers c 
Inner join accounts a
on c.customer_id = a.customer_id;

-- Display all customers and their accounts (including customers without accounts). 

select c.* , a.* 
from customers c
left join accounts a
on c.customer_id = a.customer_id;

-- Display all accounts and corresponding customer details.

select  a.* , c.*
from customers c
right join accounts a
on c.customer_id = a.customer_id;

-- Retrieve customer names and account types where balance is greater than 20,000. 

select c.name, a.account_type, a.balance 
from customers c
Inner join accounts a 
on c.customer_id = a.customer_id
where a.balance>20000;

-- List customers with their total balance using JOIN.

select c.* , sum(balance) as tot_balnce
from customers c
Inner join accounts a
on c.customer_id = a.customer_id
group by c.customer_id; 

-- Display customer names and balances sorted by balance. 

select c.name , a.balance
from customers c
Inner join accounts a
on c.customer_id = a.customer_id
order by a.balance desc;

-- Count number of accounts for each city using JOIN. 

select c.city , count(a.account_id) as count_acc
from customers c
Inner join accounts a
on c.customer_id = a.customer_id
group by c.city;

-- Find accounts with balance greater than average balance. 

select * 
from accounts 
where balance>( select avg(balance)
				from accounts ); 

-- Retrieve customers who have accounts. 

select * 
from customers 
where customer_id in ( select customer_id
						from accounts );

-- Find customers who do not have any accounts. 

select * 
from customers
where customer_id not in ( select customer_id 
							from accounts);
                            
-- Display account(s) with the maximum balance. 

select * 
from accounts 
where balance = ( select Max(balance) 
					 from accounts ) ;

-- Find customers whose total balance is greater than 40,000

select * 
from customers 
where customer_id in ( select customer_id
						from accounts 
                        group by customer_id
                        having sum(balance)>40000);


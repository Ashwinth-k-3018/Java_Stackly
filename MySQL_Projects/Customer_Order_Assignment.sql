create database Customer_Order;

use Customer_Order;

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(50),
    city VARCHAR(30)
);

INSERT INTO Customers (customer_id, customer_name, city)
VALUES
(1, 'Ashwin', 'Chennai'),
(2, 'Balaji', 'Salem'),
(3, 'Banu', 'Coimbatore'),
(4, 'Gobal', 'Chennai'),
(5, 'Karthik', 'Madurai'),
(6, 'Dinesh', 'Trichy'),
(7, 'Deepak', 'Madurai');

select * from Customers;

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    order_date DATE,
    FOREIGN KEY(customer_id) REFERENCES Customers(customer_id)
);


INSERT INTO Orders (order_id, customer_id, amount, order_date)
VALUES
(101, 1, 1500.00, '2026-01-10'),
(102, 2, 2500.00, '2026-01-15'),
(103, 1, 3000.00, '2026-02-05'),
(104, 3, 1200.00, '2026-02-12'),
(105, 5, 4000.00, '2026-03-01'),
(106, 1, 1800.00, '2026-03-05'),
(107, 1, 2200.00, '2026-03-10'),
(108, 1, 3000.00, '2026-03-11'),
(109, 6, 2900.00, '2026-03-25'), 
(110, 7, 3300.00, '2026-05-25');



select * from orders;

-- Find the total order amount for each customer.

select c.customer_name , sum(o.amount) as tot_amt
from customers c
Inner Join Orders o 
on c.customer_id = o.customer_id
group by c.customer_name;

-- Find customers who have placed more than 3 orders. 

select c.* ,  COUNT(o.order_id) AS total_orders
from customers c 
Inner join orders o 
on c.customer_id = o.customer_id
group by c.customer_id
having count(o.order_id) > 3;

-- Find the average order amount for each customer. 

select c.customer_name , Round(avg(o.amount),2) as avg_amt
from customers c
Inner Join Orders o 
on c.customer_id = o.customer_id
group by c.customer_id;

-- Find the highest order amount placed by each customer. 

select c.customer_name , Max(o.amount) as Highest_amt
from customers c
Inner Join Orders o 
on c.customer_id = o.customer_id 
group by c.customer_id;

-- Display customers sorted by their total purchase amount.
 
 select c.* , sum(o.amount) as tot_amt
 from customers c 
 Inner Join orders o
 on c.customer_id  = o. customer_id
 group by c.customer_id
 order by sum(o.amount) desc;

-- Find customers whose total purchase amount exceeds 10,000.

select c.* 
from customers c
Inner Join Orders o
on c.customer_id = o.customer_id
group by c.customer_id
having sum(o.amount)>10000;

-- Display customer names along with the total number of orders placed. 

select c.customer_name , count(o.order_id) as count_order
from customers c
Inner Join Orders o
on c.customer_id = o.customer_id 
group by c.customer_id ;

-- Find the customer who spent the highest amount.

select c.* 
from customers c 
where c.customer_id = (  select o.customer_id
						 from orders o
                         group by o.customer_id
                         order by sum(o.amount) desc
                         limit 1
                         );
 
 -- Find the customer who placed the maximum number of orders.
 
 select c.* , count(o.order_id) as tot_order
 from customers c
 Inner Join Orders o 
 on c.customer_id = o.customer_id
 group by c.customer_id
 order by  tot_order desc
 limit 1;
 
 -- Find customers whose average order amount is greater than 2,000. 
 
 select c.*, Round(avg(o.amount),2) as Avg_amt
 from customers c
 Inner join orders o 
 on c.customer_id = o.customer_id 
 group by o.customer_id
 having  avg_amt > 2000;
 
 -- Display the top 5 customers based on total purchase amount. 
 
 select c.* , sum(o.amount) as tot_amt 
 from customers c
 Inner join Orders o 
 on c.customer_id = o.customer_id 
 group by c.customer_id 
 order by tot_amt desc
 limit 5;
 
 
 -- Find the minimum order amount for each customer. 
 
 select c.* , min(o.amount) as Min_amt
 from customers c
 Inner join orders o 
 on c.customer_id = o.customer_id 
 group by c.customer_id ;
 
 -- Find customers who have placed orders worth more than 5,000 in total. 
 
 select c.* , sum(o.amount) as tot_amt 
 from customers c 
 Inner join orders o 
 on c.customer_id = o.customer_id 
 group by o.customer_id 
 having tot_amt> 5000; 
 
 -- Display customer-wise total orders and total purchase amount.
 
 select c.* , count(o.order_id) as tot_order , sum(o.amount) as tot_amt 
 from customers c
 Inner join orders o 
 on c.customer_id = o.customer_id 
 group by o.customer_id ;
 
 -- Find customers who placed more than 2 orders and spent more than 8,000. 
 
 select c.* , count(o.order_id) as tot_order , sum(o.amount) as tot_amt
 from customers c
 Inner join orders o 
 on c.customer_id = o.customer_id
 group by o.customer_id
 having tot_order > 2 and tot_amt > 8000;
create database Employee_Management; 

use Employee_Management;

CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);

INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18');

select * from employee;

-- Find the total number of employees in each department.

select department,count(emp_id) as count_emp
from employee
group by department;

-- Find the average salary of employees in each department.

select department,Round(avg(salary),2) as avg_sal
from employee
group by department;

-- Display departments having more than one employee. 

select department
from employee
group by department
having count(emp_id)>1;

-- Find the highest salary in each department.

select department , Max(salary)
from employee
group by department;

-- Find the lowest salary in each department.

select department, Min(Salary)
from employee
group by department;

-- Find departments whose average salary is greater than 50,000.

select department 
from employee 
group by department
having avg(salary)>50000;

-- Calculate the total salary expenditure for each department. 

select department , Sum(Salary)
from employee
group by department;

-- Display all employees sorted by salary in descending order.

select * 
from employee
order by salary desc;

-- Display employees sorted first by department and then by salary in descending order. 

select * 
from employee
order by department asc , salary desc;

-- Find cities that have more than one employee. 

select city 
from employee
group by city 
having count(emp_id) > 1;

-- Find the total salary paid in each city. 

select city , sum(salary) as tot_sal
from employee
group by city;

-- Display departments ordered by total salary expenditure from highest to lowest.

select department , sum(salary) as tot_sal
from employee
group by department 
order by sum(salary) desc;

-- Find the number of employees in each department whose salary is greater than 50,000.

select department , count(emp_id)
from employee 
where salary>50000
group by department ;

-- Find the difference between the highest and lowest salary in each department. 

select department , Max(salary) as Highest_sal ,
	    Min(Salary) as Lowest_sal , Max(salary) - Min(salary) as Different_sal
from employee
group by department;

-- Display the top 3 highest-paid employees.

select * from employee
order by salary desc
limit 3; 
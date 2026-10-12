create database Students_Table;

use Students_table;

CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT
);

INSERT INTO Students (student_id, student_name, department, marks)
VALUES
(1, 'Suriya', 'CSE', 85),
(2, 'Santhiya', 'ECE', 78),
(3, 'Thanusri', 'CSE', 92),
(4, 'Arun Kumar', 'IT', 67),
(5, 'Selambu', 'ECE', 88),
(6, 'Gokul Priya', 'IT', 74),
(7, 'Kowsi', 'IT', 74),
(8, 'Vicky', 'IT', 65),
(9, 'Harish', 'IT', 91),
(10, 'Nantha', 'Civil', 70),
(11, 'Suresh', 'BME', 88),
(12, 'Kowsi', 'IT', 88),
(13, 'Vinoth', 'IT', 88),
(14, 'Padma', 'IT', 88);

select * from students;

-- Find the average marks scored by students in each department.

select department , avg(marks) as Avg_mark
from students 
group by department;

-- Find departments whose average marks are above 75. 

select department,avg(marks) as Avg_mark
from students 
group by department 
having Avg_mark > 75;

-- Find the highest mark scored in each department. 

select department,Max(marks) as Highest_mark 
from students
group by department;
                
-- Find the total number of students in each department. 

select department , count(student_id) as tot_count
from students 
group by department;

-- Find departments having more than 5 students.                 

select department , count(student_id) as tot_count
from students
group by department 
having tot_count > 5;

-- Display departments sorted by average marks in descending order. 

select department , avg(marks) as Avg_mark
from students 
group by department
order by Avg_mark desc;

-- Find the top 3 departments based on average marks. 

select department , avg(marks) as Avg_mark
from students 
group by department
order by Avg_mark desc
limit 3;

-- Find departments whose average marks are between 70 and 90.

select department , Avg(marks) as Avg_mark
from Students
group by department
having Avg_mark between 70 and 90; 

-- Find the total marks scored by students in each department. 

Select department, SUM(marks) AS total_marks
From Students
Group by department;

-- Display departments sorted by the total number of students

select department , count(student_id) as tot_count
from students 
group by department 
order by tot_count desc;

-- Find the lowest mark scored in each department. 

select department , min(marks) as lowest_mark
from students
group by department ;

-- Find departments where the highest mark is greater than 90. 

select department , Max(marks) as highest_mark
from Students
group by department
having MAX(marks) > 90;

-- Find the number of students scoring above 80 in each department. 

select department , count(student_id) as tot_count
from students s
where marks > 80
group by department ;

-- Find departments where more than 3 students scored above 75. 

select department , count(student_id) as tot_count
from students 
where marks > 75
group by department
having  tot_count > 3;

-- Display departments ordered by highest mark in descending order. 

select department , Max(marks) as highest_mark
from students 
group by department 
order by highest_mark desc;
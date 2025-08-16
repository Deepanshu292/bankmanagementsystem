-- Delete old database if it exists
DROP DATABASE IF EXISTS bankmanagementsystem;

-- Create new database
CREATE DATABASE bankmanagementsystem;
USE bankmanagementsystem;

-- Table for Signup Page 1
CREATE TABLE signup(
    formno VARCHAR(20),
    name VARCHAR(50),
    fname VARCHAR(50),
    dob VARCHAR(20),
    gender VARCHAR(10),
    email VARCHAR(50),
    marital VARCHAR(15),
    address VARCHAR(100),
    city VARCHAR(30),
    pincode VARCHAR(10),
    state VARCHAR(30)
);

-- Table for Signup Page 2
CREATE TABLE signup2(
    formno VARCHAR(20),
    religion VARCHAR(20),
    category VARCHAR(20),
    income VARCHAR(20),
    education VARCHAR(30),
    occupation VARCHAR(30),
    pan VARCHAR(20),
    aadhar VARCHAR(20),
    seniorcitizen VARCHAR(5),
    existingaccount VARCHAR(5)
);

-- Table for Signup Page 3
CREATE TABLE signup3(
    formno VARCHAR(20),
    accountType VARCHAR(40),
    cardnumber VARCHAR(25),
    pin VARCHAR(10),
    facility VARCHAR(200)
);

-- Login table (used for authentication)
CREATE TABLE login(
    cardnumber VARCHAR(25),
    pin VARCHAR(10)
);

-- Bank table for transactions
CREATE TABLE bank(
    pin VARCHAR(10),
    date VARCHAR(50),
    type VARCHAR(20),
    amount VARCHAR(20)
);
show tables;

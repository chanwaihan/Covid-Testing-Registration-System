# Covid-Testing-Registration-System
<pre>
  <code>
  +---------------------------------------+
  |                                       |
  |    Welcome to FIT3077 Covid System!   |
  |    (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ ✧ﾟ･: *ヽ(◕ヮ◕ヽ)   |
  |                                       |
  +---------------------------------------+
  </code>
</pre>
Monash University Malaysia<br>
FIT3077 Software Engineering: Architecture and Design<br>
<br>
Project Duration: Mar 2022 - May 2022

## Table of Contents
* [Description](#description)
* [Features](#features)
* [Setup and Usage](#setup-and-usage)
* [Design Rationale](#design-rationale)
* [Team Members](#team-members)
* [Screenshots](#screenshots)

## Description
An online Covid Testing Registration System for the public to use.<br><br>
A public member can book for covid testing through it. Using the system, a resident can register for visiting COVID testing sites. A resident can view the available testing sites in a list. A resident can search the testing sites using suburb name, or the type of facility. The system will then display the details of the testing site queried.<br>
<br>
The program acts as a client to a web service using the REST protocol (REST API returns JSON objects).
The program should not make excessive/calls to the web service.
The program has a user interface in the console.

## Features
### 1. Login subsystem <i>(Customer, Receptionist, Healthcare Worker)</i>
The Covid Testing Registration System has a simple login system for different invdividual roles. This application acts as an intermediary between the web service, and the actual users of the app.<br>

#### Create new account
The user has to provide the given name, family name, username, password, and phone number.

#### Login
The user has to provide the username, and password.

### 2. Search for testing sites <i>(Customer)</i>
Before visiting a facility, customers can view the list of testing sites in the vicinity. This can be done by searching for the testing facility.<br>
Customers can perform partial search.<br>
e.g. "clay" for "Clayton", "university" for "Monash University"<br><br>
The customer has to provide the suburb name, and the type of facility<br>
The system returns the name, description, street, suburb, phone number, opening hours, and waiting time of the queried testing site.<br>

### 3. On-site Booking <i>(Receptionist)</i>
Receptionists can make on-site bookings for customers, assuming the customer has reached the testing site.<br><br>
The receptionist has to provide the customer ID, and the testing site ID.

### 4. On-site Testing <i>(Healthcare Workers)</i>
Healthcare workers will conduct a brief interview of the customer and based on the answers, fill a form on the system and suggest the appropriate tests.<br><br>
If a customer has severe symptoms, a PCR test is assigned.<br>
If a customer has no severe symptoms, an RAT test is assigned.

### 5. Home Booking Subsystem <i>(Customer)</i>
Customers can book home testing. Once logged in, the customer has to indicate that they are registering for home testing. Once the booking is confirmed, the customer will be given a QR code and a URL to connect for testing. The URL will also be emailed and texted to the customer. The customer will have to indicate if they already possess their own testing kit.

### 6. Booking Modifications <i>(Customer, Receptionist)</i>

#### Customers
Customers can log in and modify bookings made by them through the system. Customers can either check their current booking status through the 'profile', under current active bookings, or search manually search through the system by providing the booking ID. They can only make changes to bookings from the current day date onwards, i.e. they cannot make changes to bookings with an older date from the current day's date.<br><br>
Customers can modify the test venue (testing site) by providing a different testing site.<br>
Customers can either edit, or delete an active booking.

#### Receptionist
Receptionists can assist the customer in modifying their booking. Customers will first provide the booking ID and the PIN code to the receptionist to verify the status of the booking (confirm that it is a valid booking where users haven't gotten tested yet). If the booking has lapsed, it will be a lapsed booking and cannot be modified anymore.<br><br>
Receptionists can modify the test venue (testing site) by providing a different testing site.
Receptionists can either edit, or delete a customer's active booking.

## Setup and Usage
The program cannot be run anymore as the web service had been taken down after the end of semester.

## Design Rationale
Please refer to the <a href="https://github.com/chanwaihan/Covid-Testing-Registration-System/blob/main/project-master/design-rationale/DesignRationale.pdf">Design Rationale</a> for an in-depth explanation of the design principles, design patterns, package-level design principles, and software architectural patterns that we implemented into the system.

## Team Members
Group: MA_Lab04Team15
| Name            | Execution Role                                                                                                           |
|-----------------|--------------------------------------------------------------------------------------------------------------------------|
| Chan Wai Han    | <ul><li>Login subsystem</li><li>Search for testing sites</li><li>Booking Modifications</li></ul>                         |
| Lee Chang Horng | <ul><li>On-site Booking</li><li>On-site Testing</li><li>Home Booking Subsystem</li><li>Convert system into MVC</li></ul> |

## Screenshots
To be added.

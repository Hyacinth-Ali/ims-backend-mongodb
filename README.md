# Inventory Management System Backend Application

This is a backend application which uses MongoDB with Spring Boot framework to manage motorcycle parts inventories. This is a personal project which targets a specific motorcycle parts store. See details below to learn more about the implementation of the project.

## Architectural Design
This project follows MVC pattern, i.e., Model View Controller pattern, to design the architecture of the system. The MVC pattern promotoes decoupling of software artefacts to facilitate the evolution of the system. With this pattern, the view, i.e., the user interface, can only communicate with the controller, which then communicates with the model. In addition, we incorporate data transfer object (DTO) pattern to add flexibility in communication between the view and the model, via the controller. Below is the architectural design of the project.


![IMS Architecture](https://user-images.githubusercontent.com/24963911/214976041-2da49bf5-c57f-4cb0-8c53-811cad10796d.png)

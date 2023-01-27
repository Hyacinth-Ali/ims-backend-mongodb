# Inventory Management System Backend Application

This is a backend application which uses MongoDB with Spring Boot framework to manage motorcycle parts inventories. This is a personal project which targets a specific motorcycle parts store. See details below to learn more about the implementation of the project.

## Architectural Design
This project follows MVC pattern, i.e., Model View Controller pattern, to design the architecture of the system. The MVC pattern promotoes decoupling of software artefacts to facilitate the evolution of the system. With this pattern, the view, i.e., the user interface, can only communicate with the controller, which then communicates with the model. In addition, we incorporate data transfer object (DTO) pattern to add flexibility in communication between the view and the model, via the controller. Below is the architectural design of the project.


![IMS Architecture](https://user-images.githubusercontent.com/24963911/215000532-7f4111ae-b0a3-4206-9a39-b66488a0f3ab.png)

The dotted arrows represent dependencies between components (unless specified otherwise on the arrow).

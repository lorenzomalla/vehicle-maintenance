# Planning-Event

Planning-Event is a Java application based on Spring Boot that allows users to create and manage events. The application uses Swagger OpenAPI for API documentation, Maven for dependency management, Postgres as a relational database, Lombok to reduce code verbosity, and Mapstruct for object mapping.

## Getting Started

To use the application, you must have Java 1.8 or 11 and Maven installed on your system. Additionally, you need to create an empty Postgres database and set the login credentials in the `application.properties` file.

To run the application, clone the repository and then run the following command:


The application will be available at http://localhost:`${server.port}` (see in application.yml). 

## API Documentation

The API documentation is available at http://localhost:`${server.port}`/swagger-ui.html.

## Generate API Models and Interfaces with Swagger
You can use the Swagger Codegen plugin for Maven to automatically generate models and API interfaces based on your Swagger/OpenAPI specification.

## Contributing

To contribute to the application, follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request

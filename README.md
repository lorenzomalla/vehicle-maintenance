# Vehicle-Maintenance

Vehicle-Maintenance is a Java application based on Spring Boot that allows users to create and manage events. The application uses Swagger OpenAPI for API documentation, Maven for dependency management, Postgres as a relational database, Lombok to reduce code verbosity, and Mapstruct for object mapping.

## Getting Started

To use the application, you must have Java 1.8 or 11 and Maven installed on your system. Additionally, you need to create an empty Postgres database and set the login credentials in the `application.properties` file.

Before starting the application, execute the command mvn clean install. By configuring the plugin in the pom.xml file, this command will generate the interfaces for your APIs and the required models.

```sh
mvn clean install
```

To run the application, clone the repository and then run the following command:

```sh
mvn spring-boot:run
```

The application will be available at http://localhost:`${server.port}` (see in application.yml). 

## API Documentation

The API documentation is available at http://localhost:`${server.port}`/swagger-ui.html.

## Generate API Models and Interfaces with Swagger
You can use the Swagger Codegen plugin for Maven to automatically generate models and API interfaces based on your Swagger/OpenAPI specification.

## Security
For API security, Vehicle-Maintenance uses Spring Security and JWT. To access the APIs, include the JWT token in the Authorization header of your requests. The token can be obtained by sending a POST request to `/api/auth/signin` with a JSON body containing the username or e-mail and password.

## Contributing

To contribute to the application, follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request

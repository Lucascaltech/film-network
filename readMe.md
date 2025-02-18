# Film Network

Film Network is a web application designed to connect film enthusiasts, enabling them to share and discuss their favorite movies. The platform allows users to create profiles, upload film content, and engage with a community of like-minded individuals.

## Features


- **Film Uploads**: Share your favorite films with the community.
- **Rent Film**: Borrow a film from community member.
- **Return Film**: Return borrowed film back.
- **User Management**: Allow users to create and verify their accounts.

## Project Structure

The repository is organized into the following main directories and files:

### 1. `film-network/`

This directory houses the backend codebase of the application, developed in Java using the Spring Boot framework. The project follows a domain-based package structure, organizing code by feature areas.

- **`film-network/`**
  - **`src/`**
    - **`main/`**
      - **`java/`**
        - **`com/luca/film/`**
          - **`FilmNetworkApplication.java`**:  
            The entry point of the Spring Boot application.
          
          - **`common/`**:  
            Contains utility classes and base entities used throughout the project.
            - **`BaseEntity.java`**:  
              A base class for all entities, providing common fields like `id`, `createdAt`, and `updatedAt`.
            - **`PageResponse.java`**:  
              A generic class for paginated API responses.
          
          - **`config/`**:  
            Contains configuration classes for the application.
            - **`ApplicationAuditAware.java`**:  
              Configures auditing by providing the current auditor.
            - **`BeansConfig.java`**:  
              Defines beans and their configurations.
            - **`OpenApiConfig.java`**:  
              Configures OpenAPI/Swagger documentation for the API.

          - **`feedback/`**:  
            Manages film feedback-related functionalities.
            - **`dto/`**:  
              Data Transfer Objects for feedback operations.
              - **`FilmFeedbackRequest.java`**:  
                Represents the payload for submitting film feedback.
              - **`FilmFeedbackResponse.java`**:  
                Represents the response containing feedback details.
            - **`FilmFeedback.java`**:  
              Entity class representing feedback on films.
            - **`FilmFeedbackController.java`**:  
              Handles HTTP requests related to film feedback operations.
            - **`FilmFeedbackMapper.java`**:  
              Maps between feedback entities and DTOs.
            - **`FilmFeedbackRepository.java`**:  
              Interface for accessing feedback data, extending JPA repository.
            - **`FilmFeedbackService.java`**:  
              Contains business logic for managing film feedback.
          
          - **`file/`**:  
            Manages file storage and retrieval functionalities.
            - **`FileStorageService.java`**:  
              Service class for handling file storage operations.
            - **`FileUtils.java`**:  
              Utility class providing helper methods for file operations.
          
          - **`film/`**:  
            Manages film-related functionalities.
            - **`dto/`**:  
              Data Transfer Objects for film operations.
              - **`FilmRentalHistoryRequest.java`**:  
                Represents the payload for renting a film.
              - **`FilmRentalHistoryResponse.java`**:  
                Represents the response containing rental history details.
              - **`FilmRequest.java`**:  
                Represents the payload for creating or updating a film.
              - **`FilmResponse.java`**:  
                Represents the response containing film details.
              - **`RentedFilmResponse.java`**:  
                Represents the response for rented films.
            - **`enums/`**:  
              Enumerations used in film operations.
              - **`Genre.java`**:  
                Enum defining various film genres.
            - **`exceptions/`**:  
              Custom exceptions related to film operations.
              - **`OperationNotPermittedException.java`**:  
                Exception thrown when an operation is not allowed.
            - **`mapper/`**:  
              Maps between film entities and DTOs.
              - **`FilmMapper.java`**:  
                Maps film entities to DTOs and vice versa.
              - **`FilmRentalHistoryMapper.java`**:  
                Maps rental history entities to DTOs and vice versa.
            - **`Film.java`**:  
              Entity class representing a film.
            - **`FilmController.java`**:  
              Handles HTTP requests related to film operations.
            - **`FilmRentalHistory.java`**:  
              Entity class representing the rental history of films.
            - **`FilmRentalHistoryController.java`**:  
              Handles HTTP requests related to film rental history operations.
            - **`FilmRentalHistoryRepository.java`**:  
              Interface for accessing film rental history data, extending JPA repository.
            - **`FilmRentalHistoryService.java`**:  
              Contains business logic for managing film rentals and their history.
            - **`FilmRepository.java`**:  
              Interface for accessing film data, extending JPA repository.
            - **`FilmService.java`**:  
              Contains business logic for managing films.
          
          - **`handler/`**:  
            Handles global exceptions and error responses.
            - **`BusinessErrorCodes.java`**:  
              Defines error codes used across the application.
            - **`ExceptionResponse.java`**:  
              Represents the structure of error responses.
            - **`GlobalExceptionalHandler.java`**:  
              Global exception handler for managing exceptions and sending appropriate responses.
          
          - **`security/`**:  
            Configures security aspects of the application.
            - **`KeycloakJwtAuthenticationConverter.java`**:  
              Converts Keycloak JWT tokens into Spring Security authentication objects.
            - **`SecurityConfig.java`**:  
              Configures security settings, including authentication and authorization mechanisms.
      
      - **`resources/`**
        - **`application.yml`**:  
          Main configuration file for the Spring Boot application, defining properties like database configurations, server settings, and more.
        - **`templates/`**:  
          Directory for server-side templates (e.g., Thymeleaf), if used.
        - **`static/`**:  
          Directory for static resources like CSS, JavaScript, and images.
    
    - **`test/`**
      - **`java/`**
        - **`com/luca/film/`**
          - **`FilmNetworkApplicationTests.java`**:  
            Contains unit and integration tests for the application.

### 2. `film-network-ui/`

This directory contains the frontend codebase, developed using Angular with TypeScript, HTML, and SCSS.

- **`film-network-ui/`**
  - **`src/`**
    - **`app/`**:  
      Main application module and component files.
      - **`auth/`**:  
        Manages authentication-related functionalities.
        - **`auth.guard.ts`**:  
          Route guard to protect authenticated routes.
        - **`no-auth.guard.ts`**:  
          Route guard to prevent authenticated users from accessing certain routes (e.g., login or registration).
      - **`common/`**:  
        Contains shared components and utilities.
        - **`components/`**:  
          Reusable UI components.
          - **`loader/`**:  
            Component displaying a loading indicator.
          - **`snackbar/`**:  
            Component for displaying brief messages or notifications.
      - **`modules/`**:  
        Feature modules encapsulating specific functionalities.
        - **`film/`**:  
          Manages film-related features.
          - **`components/`**:  
            UI components specific to film features.
            - **`add-film-modal/`**:  
              Component for adding a new film.
            - **`feedbacks/`**:  
              Component displaying feedback for a film.
            - **`film-card/`**:  
              Component representing a film in a card format.
            - **`film-info-modal/`**:  
              Component displaying detailed information about a film.
            - **`menu/`**:  
              Component for navigation or action menus related to films.
            - **`return-film-modal/`**:  
              Component for handling the return of a rented film.
          - **`pages/`**:  
            Page components representing different views.
            - **`all-films/`**:  
              Page displaying all available films.
            - **`borrowed-films/`**:  
              Page showing films currently borrowed by the user.
            - **`main/`**:  
              Main or landing page for the film module.
            - **`my-films/`**:  
              Page displaying films uploaded by the user.
            - **`returned-films/`**:  
              Page showing films that have been returned.
            - **`wishlist/`**:  
              Page displaying films the user has added to their wishlist.
          - **`film.module.ts`**:  
            Angular module that declares and encapsulates all film-related components, pages, and services.
          - **`film-routing.module.ts`**:  
            Defines the routing configuration for the film module.
      
      - **`pages/`**:  
        Contains additional page components that are not part of a specific feature module.
      
      - **`services/`**:  
        Contains global services used across the application (e.g., HTTP interceptors, data services).
      
      - **`app.component.html`**:  
        The root component's HTML template.
      
      - **`app.component.scss`**:  
        Styles for the root component.
      
      - **`app.component.spec.ts`**:  
        Unit tests for the root component.
      
      - **`app.config.ts`**:  
        Configuration file for application-wide settings.
      
      - **`app.routes.ts`**:  
        Defines the routing configuration for the application.
      
      - **`app.module.ts`**:  
        The main Angular module that bootstraps the application.
    
    - **`openapi/`**:  
      Contains API specification files.
      - **`openapi.json`**:  
        OpenAPI specification file for the backend API.
    
    - **`styles.scss`**:  
      Main SCSS file for global styles.
    
    - **`index.html`**:  
      The main HTML file that bootstraps the frontend application.
    
    - **`main.ts`**:  
      The entry point for the Angular application.
  
  - **`angular.json`**:  
    Configuration file for Angular CLI projects, defining project structure and build options.
  
  - **`package.json`**:  
    Lists npm dependencies and scripts for the frontend project.
  
  - **`tsconfig.json`**:  
    TypeScript compiler configuration file.

### 3. `uploads/`

This directory is designated for storing uploaded film content, such as movie posters, trailers, or other media related to films shared within the community.

### 4. `docker-compose.yml`

A YAML file that defines services, networks, and volumes for Docker containers. It facilitates the orchestration of multi-container Docker applications, allowing for seamless integration and deployment of both backend and frontend services.

### 5. `qodana.yaml`

Configuration file for JetBrains Qodana, a code quality monitoring tool. This file specifies the settings and rules for static code analysis, helping maintain code quality and consistency across the project.

## Technologies Used

- **Backend**: Java with Spring Boot
- **Frontend**: Angular (TypeScript, HTML, SCSS)
- **Database**: PostgreSQL
- **Containerization**: Docker
- **Authentication & Authorization**: Keycloak

## Getting Started

To set up the project locally:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Lucascaltech/film-network.git
   cd film-network
   ```

2. **Backend Setup:**

   Navigate to the backend directory:

   ```bash
   cd film-network
   ```

   Build the backend application using Maven:

```bash
   ./mvnw clean install
   ```
   Run the Spring Boot application:
```bash
   ./mvnw spring-boot:run
   ```
   The backend server should now be running on http://localhost:8080.

3. **Frontend Setup:**

   Open a new terminal window and navigate to the frontend directory:
```bash
   cd film-network-ui
   ```
   Install the required npm packages:
```bash
   npm install
   ```
   Start the Angular development server:
```bash
   ng serve
   ```
   The frontend application should now be running on http://localhost:4200.

4. **Docker Setup:**

   Ensure Docker is installed and running on your system. From the root directory of the project, run:
```bash
   docker-compose up --build
   ```
   This command builds and starts both the backend and frontend services as defined in the docker-compose.yml file. Once the containers are up, the backend will be available on http://localhost:8080 and the frontend on http://localhost:4200.

5. **Access the Application:**

   Open your browser and navigate to http://localhost:4200 to access Film Network.


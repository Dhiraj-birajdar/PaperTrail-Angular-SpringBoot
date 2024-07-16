# Papertrail-angular-springboot


Papertrail-angular-springboot is a full-stack project for book sharing among students. It allows users to log in, share books, borrow books, and manage their book collections. The project is built using Angular for the frontend and Spring Boot for the backend, with Keycloak for security and MySQL as the database.

## Tech Stack

### Languages and Frameworks

- **Java** 17
- **TypeScript** 
- **Angular** 16
- **Spring Boot** 3.3.0
- **Spring Security**
- **Spring Validation**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **JPA**


### Tools and Libraries

- **Keycloak**
- **Swagger**
- **OpenAPI**
- **H2 Database**
- **MySQL**
- **Lombok**

### Development Tools

- **IntelliJ IDEA**
- **Maven**
- **Postman**
- **Git**
- **H2 Database Console**


**Features**

* User Management: Login, Registration, Forgot Password functionality with Keycloak integration.
* Book Management:
    * Add new books
    * Edit existing book details
    * Share books with other users
    * Borrow books from other users
    * Return borrowed books
    * Approve returned book requests (for lenders)
    * Set book shareability (shareable or archived)
* Open API Documentation: Swagger UI provides a user-friendly interface to explore REST API endpoints.

**Prerequisites**

* Node.js and npm (or yarn) installed (https://nodejs.org/en/download/package-manager)
* Java 11+ (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* Maven (Optional) (https://maven.apache.org/download.cgi)
* Keycloak (https://www.keycloak.org/downloads)
* MySQL database server (https://dev.mysql.com/downloads/installer/) (or compatible alternative)
* (Optional) Docker (for keycloak, mysql) (https://www.docker.com/)

**Setup Instructions**

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Dhiraj-birajdar/PaperTrail-Angular-SpringBoot.git

2. **Set Up Backend (papertrail-springboot-restapi):**
   * Configure Database Connection:
     * Update application.properties (located in papertrail-springboot-restapi/src/main/resources) with your MySQL connection details (host, port, username, password, and database name).
   
   * Generate Keycloak Configuration:
     * Follow Keycloak's setup guide (https://www.keycloak.org/getting-started/getting-started-zip) to create a Keycloak server instance.
     * import keycloak.json (located in papertrail-angular-springboot/keycloak/keycloak.json) to configure 
       server URL, 
            realm name, and client ID.
   * Build and Run Backend:
     * Open a terminal window.
     * Navigate to the papertrail-springboot-restapi directory using the cd command as shown previously.
     * Run the following commands to build and start the backend application:
     ```bash
     ./mvn clean install
     ./mvn spring-boot:run     

    * This will start the Spring Boot application, which will connect to Keycloak for authentication and your MySQL 
   database for data storage.

3. **Set Up Frontend (papertrail-angular-gui):**
    * Install Dependencies:

       ```bash
      cd papertrail-angular-gui
      npm install 

    * Build and Run Frontend:

       ```bash
          ng serve 

   * This will start the Angular development server, typically accessible at http://localhost:4200 by default.
4. **Accessing the Application**

- Once both backend and frontend are running:

- Navigate to http://localhost:4200 in your browser.
- Follow Keycloak's login flow to register or log in.
You should be able to access the Papertrail book sharing application and explore its features.
5. **Swagger UI for API Documentation:**

- Access Swagger UI at http://localhost:8080/swagger-ui/index.html (assuming your backend runs on port 8080). Explore 
the available REST API endpoints for managing users, books, and book sharing functionalities.
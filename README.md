# Clojure DDD Hexagonal Architecture REST API
Author: Alejandro Guerrero - [LinkedIn](https://www.linkedin.com/in/gbrayhan/)
Email: gbrayhan@gmail.com 

This is a Clojure-based REST API following Hexagonal Architecture principles. The project provides an implementation for managing users and their data in a PostgreSQL database using Clojure and Docker.

## Project Overview

This project is built with a focus on:

- **Hexagonal Architecture**: Ensures that the application core is independent of external technologies like databases, frameworks, or UI.
- **Clojure**: The language used to implement the application.
- **PostgreSQL**: The relational database used for data persistence.
- **Docker**: The application is containerized for easy deployment.

The API allows for the management of user data, including the ability to create, retrieve, update, and delete users.

## Project Structure

- **`src/clojure_ddd_hexagonal`**: The main application code.
    - **`application/usecase`**: Contains use cases (business logic) for user operations.
    - **`domain`**: Contains the domain model, including the user entity and service interfaces.
    - **`infrastructure`**: Includes the repository and REST controllers for handling HTTP requests.
- **`resources`**: Configuration files like `migratus.edn` and `application.properties`.
- **`migrations`**: SQL scripts for database migrations.
- **`Dockerfile`**: The Docker configuration for building and running the application.
- **`docker-compose.yml`**: Configuration to run the app and database in Docker containers.
- **`project.clj`**: The project configuration file for Leiningen (Clojure build tool).
- **`entrypoint.sh`**: Shell script to handle startup tasks like waiting for the database and running migrations.

## Hexagonal Architecture

In Hexagonal Architecture, the core of the application (business logic) is decoupled from external frameworks, databases, and other infrastructure. This design allows for flexibility and testability by separating concerns into different layers.

- **Core**: Contains the core business logic and application use cases (e.g., create, update, delete, and fetch users).
- **Adapters**: Exposes the application to external systems like databases, HTTP requests, and other services. This includes the repository layer (PostgreSQL) and the REST API controllers.

## How to Run the Project

### Prerequisites

Ensure that you have Docker and Docker Compose installed on your machine.

### Steps

1. Clone the repository.
2. Build the Docker containers using Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. This will:
    - Build the application image.
    - Start the PostgreSQL database and the application.
    - Run any required migrations.

4. Once the application is running, you can access it via `http://localhost:3000`.

### Docker Compose

The `docker-compose.yml` file defines the following services:

- **db**: The PostgreSQL database.
- **app**: The REST API application that communicates with the database.

```yaml
services:
  db:
    image: postgres:15
    container_name: hexagonal_clojure_db
    environment:
      POSTGRES_USER: usuario
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mi_api_rest_db
    volumes:
      - db-data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    networks:
      - app-network

  app:
    build: .
    container_name: hexagonal_clojure_rest_app
    environment:
      DATABASE_URL: "jdbc:postgresql://db:5432/mi_api_rest_db"
    ports:
      - "3000:3000"
    depends_on:
      - db
    networks:
      - app-network

volumes:
  db-data:

networks:
  app-network:
```

### Migrations

The project uses the **Migratus** library for database migrations. These migrations can be applied with the following command:

```bash
docker-compose run app lein migrate
```

You can also roll back migrations using:

```bash
docker-compose run app lein rollback
```

## API Endpoints

The API has the following endpoints for managing users:

### Create a New User
```bash
curl -X POST http://localhost:3000/user \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
```

### Get All Users
```bash
curl http://localhost:3000/user
```

### Get User by ID
```bash
curl http://localhost:3000/user/{id}
```

### Update a User
```bash
curl -X PUT http://localhost:3000/user/{id} \
  -H "Content-Type: application/json" \
  -d '{"name": "John Updated", "email": "john.updated@example.com"}'
```

### Delete a User
```bash
curl -X DELETE http://localhost:3000/user/{id}
```

## Architecture Overview

The architecture of the project is based on **Hexagonal Architecture**, where the core business logic (e.g., user management) is isolated from the external systems (database, web framework). The architecture includes:

- **Core (Use Cases)**: Defines the operations that can be performed on users.
- **Ports**: Interfaces that define how the core interacts with the outside world (e.g., HTTP API, repositories).
- **Adapters**: Implement the interfaces defined in the ports to interact with external systems (e.g., PostgreSQL database, HTTP controllers).

## Additional Information

- **Database**: The application uses PostgreSQL as the data store. You can configure database credentials and connection settings in `resources/migratus.edn`.
- **Testing**: The project includes basic unit tests using Clojure's `clojure.test`. Run tests with:
  ```bash
  lein test
  ```

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

---

Feel free to modify this README file as needed based on any additional features or setup steps!
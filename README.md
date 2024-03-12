# Superhero API

This Spring Boot application provides an API for performing CRUD operations on superheroes. It allows querying, creating, updating, and deleting superheroes.


### Requirements

- Java (latest LTS version)
- Maven
- Spring Boot
- Git

## Run the application

    mvn spring-boot:run 

The application will be available at http://localhost:8082.

## Endpoint

#### Get all paginated superheroes

    GET /api/superheroes/paged?page=<PAGE_NUMBER>&size=<PAGE_SIZE>

#### Get a superhero by ID

    GET /api/superheroes/{id}

#### Get superheroes by name

    GET /api/superheroes/search?name=<SUPERHERO_NAME>

#### Create a new superhero

    POST /api/superheroes

#### Request body:

```json
{
  "id": <ID>,
  "name": "<SUPERHERO_NAME>"
}
```
#### Update a superhero

    PUT /api/superheroes/{id}

#### Request body:

```json
"<NEW_NAME>"
```
#### Delete a superhero:

    DELETE /api/superheroes/{id}

#### Testing:
The application includes unit tests for at least one service. You can run the tests with the following command:

````` bash
mvn test
`````


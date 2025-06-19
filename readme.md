# Proyecto Backend - Autonomax

Este proyecto es la API backend del sistema **Autonomax**, diseñada para la gestión de usuarios, facturas, gastos, clientes, agenda y otros aspectos relacionados con la administración de una empresa autónoma. Está desarrollada utilizando **Spring Boot** como framework principal.

## Estructura del Proyecto

El directorio `src` contiene el código fuente del proyecto y está organizado de la siguiente manera:

- **`main/java/backend/Controllers/`**: Controladores REST que gestionan las rutas de la API, como usuarios, facturas, gastos, clientes, proveedores, agenda, etc.
- **`main/java/backend/Entity/`**: Entidades JPA que representan las tablas de la base de datos.
- **`main/java/backend/Services/`**: Servicios que contienen la lógica de negocio y gestionan la interacción entre los controladores y los repositorios.
- **`main/java/backend/Repository/`**: Interfaces que extienden JpaRepository para el acceso a datos.
- **`main/resources/`**: Archivos de configuración y recursos estáticos, como `application.properties`.

## Tecnologías Utilizadas

- **Spring Boot**: Framework para el desarrollo de aplicaciones backend en Java.
- **Spring Data JPA**: Persistencia de datos y ORM.
- **Spring Security**: Seguridad y autenticación.
- **H2/MySQL**: Bases de datos soportadas.
- **JUnit 5**: Framework para pruebas unitarias.
- **Mockito**: Librería para pruebas con mocks.
- **OpenAPI/Swagger**: Documentación interactiva de la API.

## Endpoints Principales

La API expone los siguientes endpoints principales, junto con el código HTTP esperado:

### Usuarios

- **GET `/autonomax/usuarios`**  
  _Código HTTP:_ **200 OK**  
  Obtener todos los usuarios.

- **GET `/autonomax/usuario/{id}`**  
  _Código HTTP:_ **200 OK** (o **404 Not Found** si no existe)  
  Obtener un usuario por ID.

- **POST `/autonomax/nuevo_usuario`**  
  _Código HTTP:_ **201 Created** (o **400 Bad Request** si hay error)  
  Registrar un nuevo usuario.

- **POST `/autonomax/login`**  
  _Código HTTP:_ **200 OK** (o **401 Unauthorized** si credenciales inválidas)  
  Loguear un usuario.

- **PUT `/autonomax/usuario/{id}`**  
  _Código HTTP:_ **200 OK** (o **404 Not Found** si no existe)  
  Actualizar un usuario por ID.

- **DELETE `/autonomax/usuario/{id}`**  
  _Código HTTP:_ **204 No Content** (o **404 Not Found** si no existe)  
  Eliminar un usuario por ID.

### Categorías de Gastos

- **GET `/autonomax/categorias`**   
  _Código HTTP:_ **200 OK**  
  Obtener todas las categorías de gastos.

- **GET `/autonomax/categoria/{id}`**  
  _Código HTTP:_ **200 OK** (o **404 Not Found** si no existe)  
  Obtener una categoría de gastos por ID.

- **POST `/autonomax/nueva_categoria`**  
  _Código HTTP:_ **201 Created** (o **400 Bad Request** si hay error)  
  Crear una nueva categoría de gastos.

- **PUT `/autonomax/categoria/{id}`**  
  _Código HTTP:_ **200 OK** (o **404 Not Found** si no existe)  
  Actualizar una categoría de gastos.

- **DELETE `/autonomax/categoria/{id}`**  
  _Código HTTP:_ **204 No Content** (o **404 Not Found** si no existe)  
  Eliminar una categoría de gastos.

> Consulta los controladores en `src/main/java/backend/Controllers/` para ver todos los endpoints disponibles y sus tipos de respuesta.

## Cómo Ejecutar el Proyecto

1. Clona el repositorio y accede al directorio del backend.
2. Configura la base de datos en `src/main/resources/application.properties`.
3. Ejecuta el proyecto con Maven:
   ```bash
   mvn spring-boot:run
   ```
4. La API estará disponible en `http://localhost:8080/`.

## Pruebas

- Ejecuta las pruebas unitarias con:
  ```bash
  mvn test
  ```

## Documentación de la API

- La documentación OpenAPI/Swagger está disponible (si está habilitada) en:
  ```
  http://localhost:8080/swagger-ui.html
  ```

## Licencia

Este proyecto está bajo la licencia **CC BY-NC-SA 4.0**. Para más información, visita [Creative Commons](https://creativecommons.org/licenses/by-nc-sa/4.0/)

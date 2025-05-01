# Repositorio Backend
# API 
# Definición OpenAPI
## Versión: v0

### /autonomaX/usuario/{id}

#### GET
##### Sumario:

Obtener un usuario

##### Descripción:

Dado un ID devuelve un usuario, en caso contrario no devuelve nada.

##### Parámetros

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Respuestas

| Code | Description |
| ---- | ----------- |
| 200 | OK |

#### PUT
##### Sumario:

Actualizar un usuario

##### Descripción:

Actualiza el usuario mediante un ID y un objeto Usuario (obligatorio esto para actualizar).

##### Parámetros

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Respuestas

| Code | Description |
| ---- | ----------- |
| 200 | OK |

#### DELETE
##### Sumario:

Eliminar un usuario

##### Descripción:

Elimina un usuario de la base de datos. Si no lo encuentra, no hace nada.

##### Parámetros

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Respuestas

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/nuevo_usuario

#### POST
##### Sumario:

Registrar un nuevo usuario

##### Descripción

Crea un nuevo usuario validando el email, el nombre y la contraseña. Devuelve el usuario creado si todo es correcto.

##### Respuesta

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/login

#### POST
##### Sumario:

Loguear un usuario

##### Descripción:

A partir de un email y una contraseña, devuelve una respuesta afirmativa o negativa

##### Respuesta

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/usuarios

#### GET
##### Sumario:

Devolver todos los usuarios

##### Descripción:

Devuelve todos los usuarios

##### Respuesta

| Code | Description |
| ---- | ----------- |
| 200 | OK |

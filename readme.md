# Repositorio Backend
# API 
# OpenAPI definition
## Version: v0

### /autonomaX/usuario/{id}

#### GET
##### Summary:

Obtener un usuario

##### Description:

Dado un id, devuelve un el usuario, en caso contrario no devuelve nada

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

#### PUT
##### Summary:

Actualizar un usuario

##### Description:

Actualiza al usuario mediante un id y un objeto Usuario (obligatorio esto para actualizar)

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

#### DELETE
##### Summary:

Eliminar un usuario

##### Description:

Elimina al usuario de la base de datos, si no lo encuentra no hace nada

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/nuevo_usuario

#### POST
##### Summary:

Registrar un nuevo usuario

##### Description:

Crea un nuevo usuario validando el email, el nombre y la contraseña. Devuelve el usuario creado si todo es correcto.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/login

#### POST
##### Summary:

Loguear un usuario

##### Description:

A partir de un email y una contraseña devuelve una respuesta afirmativa o negativa

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /autonomaX/usuarios

#### GET
##### Summary:

Devolver todos los usuarios

##### Description:

Devuelve todos los usuarios

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

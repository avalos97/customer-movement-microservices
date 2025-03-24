# Microservicios Java - Customer-Service & Movement-Service

Este repositorio contiene dos microservicios desarrollados con Java y Spring Boot, diseñados siguiendo los principios de Arquitectura Hexagonal y utilizando Kafka para la comunicación asíncrona entre servicios.

- **customer-service**: Gestiona la creación y administración de clientes, y envía mensajes a Kafka para la creación de cuentas en el microservicio movement-service.
- **movement-service**: Gestiona cuentas y movimientos (transacciones) y consume mensajes de Kafka para la creación de cuentas.

Ambos microservicios se despliegan utilizando Docker Compose junto con PostgreSQL, Zookeeper y Kafka.

---

## Autor

**Christian Avalos**  
Java Developer  

---

## Tabla de Contenidos

- [Requisitos](#requisitos)
- [Despliegue con Docker Compose](#despliegue-con-docker-compose)
- [Ejecución](#ejecución)
- [APIs Expuestas](#apis-expuestas)
- [Pruebas](#ejecutar-pruebas-unitarias-y-de-integracion-en-local-para-el-servicio-movement-service)
- [Acceso a la Base de Datos](#acceso-a-la-base-de-datos)
- [Ejecucion de collection postman](#colección-de-postman)

---

## Requisitos

- **Docker** y **Docker Compose**
- **Java 17**
- **Maven** (para compilar y empaquetar los servicios)
- Conexión a internet para descargar dependencias y Docker images

---

## Despliegue con Docker Compose

El archivo `docker-compose.yml` despliega los siguientes servicios:

- **PostgreSQL:** Base de datos principal.
- **Zookeeper:** Requerido por Kafka.
- **Kafka:** Broker de mensajería.
- **customer-service:** Microservicio de clientes.
- **movement-service:** Microservicio de cuentas y movimientos.

## Ejecución

Clonar el repositorio desde github:
- -

  ```bash
  git clone https://github.com/avalos97/customer-movement-microservices.git
#### 1. Construir Imágenes Docker:

- Ingresar a la carpeta customer-movement-microservices y través de la consola (bash o cmd), ejecute:

   ```bash
   docker-compose build
#### 2. Levantar Contenedores

- Para levantar los contenedores en primer plano:

  ```bash
  docker-compose up
- Para ejecutarlos en segundo plano:

  ```bash
  docker-compose up -d
## Apis expuestas

- **customer-service:** [http://localhost:8080/customer-service](http://localhost:8080/customer-service)
- **movement-service:** [http://localhost:8081/movement-service](http://localhost:8081/movement-service)


## Ejecutar pruebas unitarias y de integracion en local para el servicio movement-service

- nos ubicamos en la carpeta raiz de movement-service y a travez del comando ejecutamos las pruebas:

  ```bash
  mvn test
### Acceso a la Base de Datos

- **Host:** `localhost`
- **Puerto:** `5432`
- **Nombre de Base de Datos:** `devsu_db`
- **Usuario:** `devsu_user`
- **Contraseña:** `devsuPass2@2S`

## Colección de Postman

Esta colección agrupa los endpoints principales de ambos microservicios (customer-service y movement-service) para crear, actualizar, eliminar y consultar clientes, cuentas y movimientos. A continuación se describen los pasos para ejecutar la colección y la funcionalidad de cada petición.

---

### Ejecución de la Colección

1. **Importar la Colección en Postman:**
   - Abre Postman y haz clic en "Import".
   - Selecciona el archivo JSON de la colección (ubacada en la carpeta raiz de este proyecto) o pega la ruta de la colección para importarla.
   
2. **Asegurarte de que los microservicios estén en ejecución:**
   - Levanta los contenedores con Docker Compose (o ejecuta los servicios localmente).
   - Verifica que customer-service y movement-service estén accesibles en los puertos configurados.

3. **Ejecutar las peticiones:**
   - Selecciona la petición de la lista y haz clic en "Send".
   - Observa la respuesta en la parte inferior de Postman para verificar que sea exitosa (código 200 o similar).

---

### Estructura de la Colección

La colección está organizada en secciones: **CLIENTES**, **CUENTAS**, **MOVEMENTS** y **REPORT**.

#### CLIENTES

1. **POST CREATE_CLIENT**  
   - **URL:** `/clientes`  
   - **Descripción:** Crea un nuevo cliente con la información proporcionada en el body (JSON).  
   - **Ejemplo de Body:**
     ```json
     {
       "nombre": "Juan Pérez",
       "genero": "MASCULINO",
       "edad": 30,
       "identificacion": "12345678",
       "direccion": "Calle Falsa 123",
       "telefono": "0999999999",
       "contrasena": "secret"
     }
     ```

2. **PUT UPDATE_CLIENT**  
   - **URL:** `/clientes/{id}`  
   - **Descripción:** Actualiza la información de un cliente existente, identificándolo por su ID (UUID).  Si un cliente se inactiva, se inactivan sus cuentas de manera automatica.
   - **Ejemplo de Body:**
     ```json
     {
       "nombre": "Juan Editado",
       "genero": "FEMENINO",
       "edad": 28,
       "identificacion": "99999999",
       "direccion": "Nueva Dirección",
       "telefono": "0987654321",
       "contrasena": "newsecret",
       "estado": false
     }
     ```

3. **GET GET_CLIENT**  
   - **URL:** `/clientes/{id}`  
   - **Descripción:** Retorna la información de un cliente específico, usando su ID (UUID).

4. **DELETE DELETE_CLIENT**  
   - **URL:** `/clientes/{id}`  
   - **Descripción:** Elimina un cliente por su ID. En el microservicio, también se envía un evento a movement-service para eliminar las cuentas asociadas a ese cliente.

5. **GET GET_ALL_CLIENTS**  
   - **URL:** `/clientes`  
   - **Descripción:** Lista todos los clientes registrados en la base de datos.

6. **POST CLIENT WITH ACCOUNT**  
   - **URL:** `/clientes/whit-account`  
   - **Descripción:** Crea un cliente y, simultáneamente, envía un evento a movement-service para crear una cuenta asociada.
      - **Ejemplo de Body:**
     ```json
     {
        "nombre":"test",
        "genero":"MASCULINO",
        "edad":25,
        "identificacion":"90888776688",
        "direccion":"Col Esmeralda Casa 23",
        "telefono":"766534272",
        "contrasena":"pass",
        "account":{
            "numeroCuenta":654325,
            "tipoCuenta":"CORRIENTE",
            "saldoInicial":1000
          }
      }
     ```

---

#### CUENTAS

1. **POST CREATE_ACCOUNT**  
   - **URL:** `/cuentas`  
   - **Descripción:** Crea una cuenta nueva (desde movement-service) asociada a un cliente.  
   - **Ejemplo de Body:**
     ```json
     {
       "numeroCuenta": 123456,
       "clienteId": "UUID-del-cliente",
       "tipoCuenta": "AHORRO",
       "saldoInicial": 1000.00
     }
     ```

2. **POST MOVEMENT_BY_ACCOUNT**  
   - **URL:** `/cuentas/{id}/movimiento`  
   - **Descripción:** Registra un movimiento mediante valores positivos o negativos (DEPOSITO o RETIRO) en la cuenta especificada por su ID.
   - **Ejemplo de Body:**
     ```json
      {
        "valor": 100
      }
     ```

3. **PUT EDIT_ACCOUNT**  
   - **URL:** `/cuentas/{id}`  
   - **Descripción:** Actualiza los datos de una cuenta existente (por ejemplo, cambiar el tipo de cuenta o el estado).

4. **GET GET_ACCOUNT**  
   - **URL:** `/cuentas/{id}`  
   - **Descripción:** Obtiene la información de una cuenta específica usando su ID (UUID).

5. **GET GET_ALL_ACCOUNT**  
   - **URL:** `/cuentas`  
   - **Descripción:** Retorna la lista completa de cuentas registradas.

---

#### MOVEMENTS

1. **GET GET_MOVEMENTS_BY_ID**  
   - **URL:** `/movimientos/{id}`  
   - **Descripción:** Obtiene la información de un movimiento específico usando su ID (UUID).

2. **GET GET_MOVEMENTS_BY_ACCOUNT**  
   - **URL:** `/movimientos/{id}`  
   - **Descripción:** Obtiene la información de los movimientos asociados a una cuenta

---

#### REPORT

1. **GET MOVEMENT_BY_CLIENT**  
   - **URL:** `/reportes?fechaInicio=dd-MM-yyyy&fechaFin=dd-MM-yyyy&clienteId=UUID`  
   - **Descripción:** Genera un reporte de los movimientos de las cuentas de un cliente, dentro de un rango de fechas específico.


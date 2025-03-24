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

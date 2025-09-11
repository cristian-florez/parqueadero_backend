# 🚗 Parqueadero - Backend de Gestión

Un backend diseñado para la **gestión de parqueaderos**, que permite controlar entradas, salidas, pagos y generar
reportes de cierre de turno.
Construido con **Spring Boot** y **MySQL**, el proyecto garantiza confiabilidad, escalabilidad y facilidad de
despliegue.

---

## ⭐ Característica Destacada: Generación de Tickets

La funcionalidad más importante del sistema es el **control mediante tickets**, lo que permite un registro claro y
auditable de las operaciones:

* **Ticket de Entrada**: Registra el ingreso de vehículos con datos como placa, tipo y hora.
* **Ticket de Cierre de Turno**: Resume las operaciones de un período, incluyendo vehículos atendidos y pagos
  realizados.

Esto asegura un control eficiente de la operación diaria y facilita la gestión administrativa.

---

## 🛠️ Stack Tecnológico

### Backend

* Lenguaje: **Java 21**
* Framework: **Spring Boot 3.5.5**
* Persistencia: **Spring Data JPA / Hibernate**
* Build Tool: **Maven**

### Base de Datos

* Motor: **MySQL 8**

### DevOps & Despliegue

* Contenerización: **Docker & Docker Compose**

---

## 🐳 Cómo Empezar con Docker (Recomendado)

La forma más rápida de levantar el proyecto es usando **Docker Compose**, que inicializa tanto la base de datos como el
backend.

### 1. Prerrequisitos

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)

### 2. Instalación

Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/parqueadero_backend.git
cd parqueadero_backend
```

Copia el archivo de entorno:

```bash
cp .env.example .env
```

Abre el archivo `.env` y rellena tus credenciales de MySQL y otros valores sensibles.

Levanta los servicios:

```bash
docker-compose up --build -d
```

¡Y listo! La aplicación estará disponible en:
👉 [http://localhost:8080]

---

## 🚀 Instalación Manual (Alternativa)

### 1. Prerrequisitos

* **JDK 21**
* **Maven 3+**
* **MySQL 8+**

### 2. Instalación

Clona el repositorio y crea la base de datos:

```sql
CREATE DATABASE parqueadero;
```

Configura las credenciales en `src/main/resources/application.properties` o `application.yml`.
Luego compila y ejecuta:

```bash
mvn clean package -DskipTests
java -jar target/parqueadero-0.0.1-SNAPSHOT.jar
```

La aplicación correrá en:
👉 [http://localhost:8080](http://localhost:8080)

---

## 📚 Endpoints Principales

| Método | Endpoint                     | Descripción                        |
| ------ | ---------------------------- | ---------------------------------- |
| GET    | `/api/tickets`               | Listar tickets de entrada          |
| POST   | `/api/tickets`               | Crear un nuevo ticket de entrada   |
| GET    | `/api/pagos`                 | Listar pagos registrados           |
| POST   | `/api/pagos`                 | Registrar un nuevo pago            |
| GET    | `/api/reportes/cierre-turno` | Generar reporte de cierre de turno |

---

## 🖨️ Habilitar Impresora (QZ Tray)

Por defecto, la funcionalidad para imprimir recibos a través de QZ Tray está desactivada para facilitar el inicio rápido
del backend. Para habilitarla, sigue estos pasos:

1. **Proporcionar una Clave Privada:**
   Asegúrate de tener un archivo de clave privada válido (`private-key.pem`) y colócalo en la siguiente ruta:
   ```
   src/main/resources/keys/private-key.pem
   ```
   *Este archivo está ignorado por Git por seguridad.*

2. **Activar el Controlador:**
   En el archivo `src/main/java/com/parqueadero/controllers/QzSignatureController.java`, descomenta la anotación
   `@RestController`.
   ```java
   // Cambia de:
   //@RestController
   
   // a:
   @RestController
   ```

3. **Activar la Configuración de la Clave:**
   En el archivo `src/main/java/com/parqueadero/config/QzKeyConfig.java`, descomenta la anotación `@Configuration`.
   ```java
   // Cambia de:
   //@Configuration
   
   // a:
   @Configuration
   ```

Una vez completados estos pasos, reinicia la aplicación. El endpoint para firmar las peticiones de QZ Tray estará
activo.

---

## 🚧 Estado del Proyecto y Futuro

✅ Gestión de tickets de entrada y cierre
✅ Registro de pagos asociados
✅ Reportes de cierre de turno

🔜 Mejoras previstas:

* Autenticación y control de acceso con **JWT**
* Pruebas unitarias con **JUnit & Mockito**
* Integración con **lector de códigos QR**

---

## 👨‍💻 Autor

Proyecto desarrollado por **Cristian Flórez**.

---

## 📄 Licencia

Distribuido bajo la licencia [MIT](../repositorio_cristian/parqueadero_backend/LICENSE).

---

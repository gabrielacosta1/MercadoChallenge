# Cómo Ejecutar y Probar el Proyecto

# Prerrequisitos

    Java JDK 17 o superior instalado.

    Apache Maven instalado (opcional, ya que el proyecto incluye el wrapper de Maven).

# Pasos para la Ejecución (Modo Desarrollo)

Esta es la forma recomendada para levantar la aplicación localmente mientras se desarrolla y se prueba.

    Clona o descarga el repositorio en tu máquina local.

    Abre una terminal (PowerShell, CMD, o Terminal de Linux/Mac).

    Navega hasta la carpeta raíz del proyecto (donde se encuentra el archivo pom.xml).

    Ejecuta el siguiente comando para iniciar la aplicación:

    En Windows (PowerShell):
    Bash

.\mvnw.cmd clean spring-boot:run

# En Windows (CMD):
    mvnw.cmd clean spring-boot:run

# En Linux o Mac:
    ./mvnw clean spring-boot:run

    La API estará corriendo y lista para recibir peticiones en http://localhost:8080.

# Cómo Ejecutar los Tests
El proyecto incluye una suite de tests unitarios que validan la lógica de negocio. Para ejecutarlos, usa el siguiente comando en la terminal desde la raíz del proyecto:

# En Windows (PowerShell):
    .\mvnw.cmd test

# En Windows (CMD):
    mvnw.cmd test

# En Linux o Mac:
    ./mvnw test

Maven compilará el código y ejecutará todas las pruebas. Verás un BUILD SUCCESS si todas las pruebas pasan correctamente.

# Cómo Construir el Proyecto (Build)

Si deseas generar el archivo .jar ejecutable para un despliegue en producción, sigue estos pasos.

    Abre una terminal en la raíz del proyecto.

    Ejecuta el comando de empaquetado de Maven:
    Bash

    .\mvnw.cmd clean package

    Una vez finalizado, encontrarás el archivo .jar dentro de la carpeta target/. Por ejemplo: target/MercadeandoLibre-0.0.1-SNAPSHOT.jar.

    Puedes ejecutar este archivo en cualquier máquina que tenga Java con el comando: java -jar target/MercadeandoLibre-0.0.1-SNAPSHOT.jar.

# Cómo Probar la API

Se incluye una colección de Postman en la carpeta /postman con todos los servicios listos para ser probados. Simplemente impórtala en tu aplicación de Postman.
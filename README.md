# MercadoChallenge - API para challenge de Mercado Libre

## Descripción

Este proyecto es una API RESTful desarrollada como parte de un challenge técnico. Permite gestionar un ecosistema de comercio electrónico con las siguientes funcionalidades:

    Gestión de Usuarios: Se pueden registrar usuarios con roles de COMPRADOR o VENDEDOR.

    Gestión de Productos: Los vendedores pueden agregar productos, los cuales quedan asociados a su ID.

    Carrito de Compras: Los compradores disponen de un carrito donde pueden agregar o eliminar ítems y consultar su contenido.

    Ciclo de Compra: Al finalizar la compra, el contenido del carrito se convierte en una Orden asociada al ID del comprador.

    Reseñas: Los usuarios pueden dejar reseñas (reviews) en cada producto.

## Diseño y Arquitectura

La aplicación sigue los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)** para asegurar un bajo acoplamiento y alta mantenibilidad. La estructura se divide en tres capas principales:

- **Domain:** Contiene los modelos de negocio puros (Cart, Category, Order, Product, Review, User), sin dependencias externas.

- **Application:** Orquesta la lógica de negocio a través de servicios y define los puertos (interfaces) de entrada y salida (CartPort, CategoryPort, OrderPort, ProductPort, ReviewPort, UserPort).

- **Infrastructure:** Implementa los adaptadores para tecnologías externas, como los controladores REST (entrada) (CartController, CategoryController, OrderController, ProductController, ReviewController, UserController) y la persistencia de datos en archivos JSON (salida) (CartJsonAdapter, CategoryJsonAdapter, OrderJsonAdapter, ProductJsonAdapter, ReviewJsonAdapter, UserJsonAdapter).

Esta decisión arquitectónica permite que el núcleo de la aplicación sea independiente de la tecnología, facilitando las pruebas y la evolución futura del sistema.

## Stack Tecnológico

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3
- **Dependencias Clave:**
  - `spring-boot-starter-web`: Para la creación de la API REST.
  - `spring-boot-starter-validation`: Para las validaciones de los datos de entrada.
  - `spring-boot-starter-security`: Para la gestión de la seguridad.
  - `Lombok`: Para reducir el código boilerplate en los modelos.
  - `JUnit 5 & Mockito`: Para las pruebas unitarias.

## Endpoints Principales

La API expone funcionalidades para gestionar Usuarios, Productos, Carritos, Órdenes y Reseñas. Una lista completa de los endpoints y cómo usarlos se encuentra en la colección de Postman incluida en la carpeta `/postman`.

Gestión de Usuarios (/users)

    POST /users/register: Registra un nuevo usuario (comprador o vendedor).

    GET /users: Obtiene una lista de todos los usuarios registrados.

    GET /users/{userId}: Obtiene los detalles de un usuario específico por su ID.

    PUT /users/{userId}: Actualiza la información de un usuario existente.

    DELETE /users/{userId}: Elimina un usuario.

Gestión de Productos (/products)

    POST /products: Permite a un vendedor publicar un nuevo producto.

    GET /products/my-products/{userId}: Devuelve todos los productos publicados por un vendedor específico.

    GET /products/{productId}: Obtiene los detalles de un producto específico.

    PUT /products/{productId}: Actualiza la información de un producto.

    DELETE /products/{productId}: Elimina un producto.

Gestión de Carritos (/carts)

    POST /carts/{userId}: Crea un nuevo carrito de compras para un usuario.

    POST /carts/items: Añade un producto a un carrito.

    GET /carts/{userId}: Obtiene el contenido del carrito de un usuario.

    GET /carts: Obtiene una lista de todos los carritos.

    DELETE /carts/{cartId}/items/{productId}: Elimina un item específico de un carrito.

    DELETE /carts/{userId}: Vacía todos los items del carrito de un usuario.

    POST /carts/finish-buy: Finaliza una compra y convierte el carrito en una orden.

Gestión de Órdenes (/orders)

    POST /orders: Crea una orden directamente.

    GET /orders/{orderId}: Obtiene los detalles de una orden específica.

    GET /orders/user/{userId}: Devuelve un historial de todas las órdenes de un usuario.

    PUT /orders/{orderId}/status: Cambia el estado de una orden (ej. de PENDING a SHIPPED).

Gestión de Reseñas (/reviews)

    POST /reviews: Permite a un usuario dejar una reseña sobre un producto.

    GET /reviews/{reviewId}: Obtiene una reseña específica por su ID.

    DELETE /reviews/{reviewId}: Elimina una reseña.

## Ejecutar el Proyecto

Para levantar el proyecto localmente, sigue las instrucciones detalladas en el archivo `run.md`.
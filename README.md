# Product Inventory Microservices

Documentación de los endpoints disponibles en los microservicios **Product** e **Inventory**.

---

# Product Microservice

Base path:

```text
/api/v1/products
```

## GET `/api/v1/products`

Obtiene todos los productos.

### Request

No requiere body ni parámetros.

### Response — `200 OK`

```json
[
  {
    "id": 1,
    "name": "Laptop",
    "price": 850.00,
    "stock": 10
  }
]
```

### Response fields

| Campo   | Tipo         |
| ------- | ------------ |
| `id`    | `Long`       |
| `name`  | `String`     |
| `price` | `BigDecimal` |
| `stock` | `Long`       |

Si no existen productos:

```text
204 No Content
```

---

## GET `/api/v1/products/{id}`

Obtiene un producto mediante su ID.

### Path parameters

| Parámetro | Tipo   |
| --------- | ------ |
| `id`      | `long` |

### Ejemplo

```text
GET /api/v1/products/1
```

### Response — `200 OK`

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 850.00,
  "stock": 10
}
```

### Response fields

| Campo   | Tipo         |
| ------- | ------------ |
| `id`    | `Long`       |
| `name`  | `String`     |
| `price` | `BigDecimal` |
| `stock` | `Long`       |

Si el producto no existe:

```text
404 Not Found
```

---

## POST `/api/v1/products`

Crea un nuevo producto.

### Request body

```json
{
  "name": "Laptop",
  "price": 850.00,
  "stock": 10
}
```

### Request fields

| Campo   | Tipo         |
| ------- | ------------ |
| `name`  | `String`     |
| `price` | `BigDecimal` |
| `stock` | `Long`       |

### Response — `201 Created`

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 850.00,
  "stock": 10
}
```

El header `Location` contiene la URI del recurso creado:

```text
Location: /api/v1/products/1
```

---

## PUT `/api/v1/products/{id}`

Actualiza un producto.

### Path parameters

| Parámetro | Tipo   |
| --------- | ------ |
| `id`      | `long` |

### Request body

```json
{
  "name": "Laptop Pro",
  "price": 1200.00,
  "stock": 15
}
```

### Request fields

| Campo   | Tipo         |
| ------- | ------------ |
| `name`  | `String`     |
| `price` | `BigDecimal` |
| `stock` | `Long`       |

### Response

```text
204 No Content
```

Si el producto no existe:

```text
404 Not Found
```

---

## PATCH `/api/v1/products/{id}`

Actualiza parcialmente un producto.

### Path parameters

| Parámetro | Tipo   |
| --------- | ------ |
| `id`      | `long` |

### Request body

```json
{
  "price": 1100.00
}
```

### Request fields

| Campo   | Tipo         |
| ------- | ------------ |
| `name`  | `String`     |
| `price` | `BigDecimal` |
| `stock` | `Long`       |

Los campos enviados corresponden a los valores que se desean actualizar.

### Response

```text
204 No Content
```

Si el producto no existe:

```text
404 Not Found
```

---

## DELETE `/api/v1/products/{id}`

Elimina un producto.

### Path parameters

| Parámetro | Tipo   |
| --------- | ------ |
| `id`      | `long` |

### Ejemplo

```text
DELETE /api/v1/products/1
```

### Response

```text
204 No Content
```

Si el producto no existe:

```text
404 Not Found
```

---

# Inventory Microservice

Base path:

```text
/api/v1/inventory
```

## GET `/api/v1/inventory/stocks`

Obtiene todos los registros de inventario.

### Request

No requiere body ni parámetros.

### Response — `200 OK`

```json
[
  {
    "id": 1,
    "stock": 10
  },
  {
    "id": 2,
    "stock": 25
  }
]
```

### Response fields

| Campo   | Tipo   |
| ------- | ------ |
| `id`    | `Long` |
| `stock` | `Long` |

Si no existen registros:

```text
204 No Content
```

---

## GET `/api/v1/inventory/product/{id}`

Obtiene el stock de un producto.

### Path parameters

| Parámetro | Tipo   |
| --------- | ------ |
| `id`      | `Long` |

### Ejemplo

```text
GET /api/v1/inventory/product/1
```

### Response — `200 OK`

```json
10
```

El valor recibido corresponde directamente al stock del producto.

---

## POST `/api/v1/inventory`

Crea un registro de inventario.

### Request body

```json
{
  "id": 1,
  "stock": 10
}
```

### Request fields

| Campo   | Tipo   |
| ------- | ------ |
| `id`    | `Long` |
| `stock` | `Long` |

### Response — `201 Created`

```json
10
```

El valor recibido corresponde al stock creado.

---

# Data Transfer Objects

## ProductRequestDTO

Utilizado para enviar información de productos.

```java
record ProductRequestDTO(
    String name,
    BigDecimal price,
    Long stock
)
```

## ProductResponseDTO

Utilizado para recibir información de productos.

```java
record ProductResponseDTO(
    Long id,
    String name,
    BigDecimal price,
    Long stock
)
```

## InventoryDTO

Utilizado para enviar y recibir información de inventario.

```java
record InventoryDTO(
    Long id,
    Long stock
)
```

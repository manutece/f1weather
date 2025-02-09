# Formula 1 API

## Descripción

Esta API, desarrollada en Java utilizando Spring Boot, proporciona información sobre las condiciones climáticas de carreras de Fórmula 1. La API integra dos servicios externos:

1. **OpenF1 es la API de Fórmula 1** para obtener información de sesiones y circuitos.
2. **Visual Crossing es la API del Clima** para consultar las condiciones climáticas basadas en la fecha y la ubicación de las carreras.

---

## Endpoints

### **1. Obtener información de una carrera**

**URL:** `/api/f1/carrera`

**Método:** `GET`

**Parámetros de consulta:**

- `circuitoNombre` (String): Nombre del circuito de la carrera.
- `tipoSesion` (String): Tipo de sesión (por ejemplo, "race" o "qualifying").
- `año` (int): Año de la carrera.

**Respuesta:**
Devuelve un objeto `Sesion` que incluye:

- Información de la sesión (fecha, lugar, etc.).
- Datos del clima (temperatura, condición climática, etc.).

**Ejemplo de Respuesta:**

```json
{
    "circuit_short_name": "Imola",
    "session_name": "Qualifying",
    "date_start": "2024-05-18T14:00:00+00:00",
    "country_name": "Italy",
    "clima": {
        "temp": 23.3,
        "humidity": 50.3,
        "precip": 0.0,
        "preciptype": "null",
        "windspeed": 11.5,
        "visibility": 10.0
    },
    "location": "Imola"
}
```

**Códigos de Respuesta:**

- `200 OK`: Si se obtiene la información correctamente.
- `500 INTERNAL SERVER ERROR`: Si ocurre un error.

---

### **2. Obtener los circuitos por año**

**URL:** `/api/f1/circuitos`

**Método:** `GET`

**Parámetros de consulta:**

- `año` (int): Año de los circuitos que se desean consultar.

**Respuesta:** Devuelve una lista de nombres de circuitos (`circuit_short_name`) donde se realizaron carreras durante el año especificado.

**Ejemplo de Respuesta:**

```json
[
    "Sakhir",
    "Jeddah",
    "Melbourne",
    "Suzuka",
  ...
]
```

**Códigos de Respuesta:**

- `200 OK`: Si se obtienen los circuitos correctamente.
- `500 INTERNAL SERVER ERROR`: Si ocurre un error.

---

## Tecnologías Utilizadas

- **Java** (versión 11 o superior).
- **Spring Boot** (versión 2.5 o superior).
- **HttpClient** para llamadas a APIs externas.
- **Maven** para la gestión de dependencias.
- **Jackson** para serialización/deserialización JSON.

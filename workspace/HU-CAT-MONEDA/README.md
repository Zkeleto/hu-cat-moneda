# Proyecto HU-CAT-MONEDA

Este proyecto proporciona una API REST para la gestión de monedas y sus características (como símbolo, abreviación, etc.). Está empaquetado como una imagen Docker y puede ser ejecutado en cualquier máquina que tenga Docker instalado.

## Funcionalidades

- Crear, leer, actualizar y eliminar monedas.
- API documentada con Swagger para facilitar la interacción con los endpoints.

## Cómo ejecutar el proyecto

Para ejecutar este proyecto, sigue los siguientes pasos:

### 1. Asegúrate de tener Docker instalado

Si aún no tienes Docker, puedes descargarlo desde [aquí](https://www.docker.com/get-started) `https://www.docker.com/get-started`.

### 2. Descargar la imagen desde Docker Hub

Ejecuta el siguiente comando para descargar la imagen `hu-cat-moneda` desde Docker Hub:

```bash
docker pull zkeleto/hu-cat-moneda:1.0
```
### 3. Ejecutar la imagen Docker

Una vez descargada la imagen, ejecuta el siguiente comando para iniciar el contenedor:
```bash
docker run -p 8010:8010 zkeleto/hu-cat-moneda:1.0
```
Esto mapea el puerto 8010 de tu máquina local al puerto 8010 del contenedor.

### 4. Acceder a la API

Una vez que el contenedor esté en funcionamiento, puedes acceder a la API a través de la [url](http://localhost:8010/swagger-ui/index.html). http://localhost:8010/swagger-ui/index.html
### 5. Datos de prueba
Para probar los endpoints, puedes usar los siguientes datos de prueba:

POST - Crear nueva moneda:

```bash
{
  "numCia": 1,
  "claveMoneda": "MXM",
  "descripcion": "Peso Mexicano",
  "simbolo": "$",
  "abreviacion": "MXM",
  "monedaCorriente": "S",
  "status": "A"
}
```
GET - Obtener monedas
Una vez que hayas creado una moneda, puedes hacer un GET para obtener las monedas registradas.
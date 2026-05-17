Proyecto de evaluación parcial 2 para **Desarrollo FullStack 1 (DSY1103) — Duoc UC 2026**.

Contiene un total de 11 microservicios, de los cuales (`ms-productos` y `ms-pedidos`) se comunican de forma sincrona mediante **Feign Client**, los restantes no necesitan información de sus pares para funcionar. Los 11 contenedores con cada microservicio están repartidos en 3 instancias EC2 (una de cada integrante) y para hacerlos funcionar hay que ejecutar el comando "docker compose up -d" una vez por cada contenedor. 


## Integrantes
- Alejandro Rivera
- Félix Rojas
- Tomás Gaete

## Mapa de las instancias con sus microservicios
Instancia EC2 de Alejandro Rivera
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Soporte        | 8080   | 3306     | Soporte técnico       |
| Pagos          | 8081   | 3307     | Métodos de pago       |
| Ofertastrabajo | 8082   | 3308     | Ofertas laborales     |

Instancia EC2 de Tomás Gaete
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Check in/out   | 8080   | 3306     | Check in/out          |
| Comida         | 8081   | 3307     | Menú comida           |
| Catálogo       | 8082   | 3308     | Catálogo app          |
| Notificaciones | 8083   | 3309     | Notificaciones app    |

Instancia EC2 de Félix Rojas
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Autentificador | 8080   | 3306     | inicio de sesión      |
| inventario     | 8081   | 3307     | Gestor de inventario  |
| Oferta turismo | 8082   | 3308     | Ofertas de turismo    |
| Notificaciones | 8083   | 3309     | Notificaciones app    |


## Despliegue Técnico
- **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
- **Comando de inicio:** `docker compose up -d`
- **Repositorio Maestro:** Es este mismo repositorio.

## Pasos para desplegar

1. Tener 3 instancias EC2 con los puertos mencionados en el mapa de instancias configurados correctamente. (en el caso de MySQL sólo se necesita el 3306, puesto a que este es el puerto predilecto para bases de datos de este tipo, 3307 en adelante son los puertos que utiliza el servidor de manera interna para evitar que aparezcan errores sobre ocupación de puertos.)
2. Una vez dentro del servidor, 



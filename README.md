Proyecto de evaluación parcial 2 para **Desarrollo FullStack 1 (DSY1103) — Duoc UC 2026**.

Esta es una aplicación con arquitectura de microservicios orientada a hoteles, que digitaliza y vuelve más fácil muchas de las funciones que realizan este tipo de negocios.

Contiene un total de 10 microservicios, de los cuales (`checkin/out` y `autentificacion`) se comunican de forma sincrona mediante **Feign Client**, los restantes no necesitan información de sus pares para funcionar. Los 10 contenedores con cada microservicio están repartidos en 3 instancias EC2 (una de cada integrante) y para hacerlos funcionar hay que ejecutar el comando "docker compose up -d" una vez por cada contenedor. 


## Integrantes
- Alejandro Rivera
- Félix Rojas
- Tomás Gaete

## Mapa de las instancias con sus microservicios
En este apartado hay un mapa de las 3 instancias, indicando los microservicios que poseen, sus respectivos puertos y una explicación breve de sus funciones.

Instancia A EC2 de Alejandro Rivera
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Soporte        | 8080   | 3306     | Soporte técnico       |
| Pagos          | 8081   | 3307     | Métodos de pago       |
| Ofertastrabajo | 8082   | 3308     | Ofertas laborales     |

Instancia B EC2 de Tomás Gaete
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Check in/out   | 8080   | 3306     | Check in/out          |
| Comida         | 8081   | 3307     | Menú comida           |
| Catálogo       | 8082   | 3308     | Catálogo app          |
| Notificaciones | 8083   | 3309     | Notificaciones app    |

Instancia C EC2 de Félix Rojas
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| usuarios       | 8080   | 3306     | Autentificación       |
| inventario     | 8081   | 3307     | Gestor de inventario  |
| Oferta turismo | 8082   | 3308     | Ofertas de turismo    |


## Despliegue Técnico
- **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
- **Comando de inicio:** `docker compose up -d`
- **Repositorio Maestro:** Es este mismo repositorio.

## Pasos para desplegar

1. Tener 3 instancias EC2 con los puertos mencionados en el mapa de instancias configurados correctamente. (en el caso de MySQL sólo se necesita el 3306, puesto a que este es el puerto predilecto para bases de datos de este tipo, 3307 en adelante son los puertos que utiliza el servidor de manera interna para evitar que aparezcan errores sobre ocupación de puertos.)
2. Una vez dentro de los servidores, se debe ejecutar un comando para cada instancia:
- Instancia A: `git clone https://github.com/AlejandroRivera2005/EvaluacionParcial2Fullstack/tree/main/InstanciaA`
- Instancia B: `git clone https://github.com/AlejandroRivera2005/EvaluacionParcial2Fullstack/tree/main/InstanciaB`
- Instancia C: `git clone https://github.com/AlejandroRivera2005/EvaluacionParcial2Fullstack/tree/main/InstanciaC`

3. Una vez se hayan descargado los microservicios correspondientes para cada instancia, se debe navegar hasta la carpeta de cada microservicio, y una vez estando a la misma altura que los archivos docker-compose.yml, se debe ejecutar el comando docker compose up -d.

4. Cuando estén todos los dockers creados correctamente, bastará con ejecutar las aplicaciones de Springboot por medio del Main de cada microservicio.

(EXTRA) En caso de desear tener todos los microservicios en una única instancia, se deberán configurar los puertos para ser usados de este modo, debido a que este proyecto se desarrolló en torno a 3 instancias con los microservicios repartidos dentro de estas.

Una vez estén los microservicios ejecutándose, se podrán realizar los métodos CRUD en cada uno de estos sin ningún problema.

## Conexión FEIGN
En este proyecto, la única comunicación entre microservicios necesaria fue `usuarios-checkin/out`, comunica a las instancias C y B a través de estos 2 microservicios. 

## Distribución de Commits
Este repositorio indica a Alejandro Rivera como único colaborador, esto se debe a que este integrante se ofreció voluntario para configurarlo. Sin embargo, como se aclaró al comienzo de este README, los aportes al código están distribuidos tal como lo dice el **Mapa de las instancias con sus microservicios.** Siendo cada dueño de las instancias el responsable de los microservicios dentro su propio servidor.




# Proyecto Semestral: Plataforma de gestión en línea para hoteles

## Integrantes
- Alejandro Rivera
- Félix Rojas
- Tomás Gaete

## Estado del Sistema
Instancia EC2 de Alejandro Rivera
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Soporte        | 8080   | 3306     | Soporte técnico       |
| Pagos          | 8081   | 3307     | Métodos de pago       |
| Ofertastrabajo | 8082   | 3308     | Ofertas laborales     |

Instancia EC2 de Tomás Gaete
| Microservicio  | Puerto | DB puerto| Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Soporte        | 8080   | 3306     | Soporte técnico       |
| Pagos          | 8081   | 3307     | Métodos de pago       |
| Ofertastrabajo | 8082   | 3308     | Ofertas laborales     |
| Ofertastrabajo | 8082   | 3308     | Ofertas laborales     |



## Despliegue Técnico
- **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
- **Comando de inicio:** `docker compose up -d`
- **Repositorio Maestro:** Es este mismo repositorio.

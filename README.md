# Proyecto Semestral: Plataforma de gestión en línea para hoteles

## Integrantes
- Alejandro Rivera
- Félix Rojas
- Tomás Gaete

## Estado del Sistema (Hito 1.5)
Instancia EC2 de Alejandro Rivera
| Microservicio  | Puerto | DB Name  | Funcionalidad (cruds) |
| :------------- | :----- | :------- | :-------------------- |
| Soporte        | 8080   | auth_db  | Soporte técnico       |
| Pagos          | 8081   | [DB]     | Métodos de pago       |
| Ofertastrabajo | 8082   | [DB]     | Ofertas laborales     |


## Despliegue Técnico
- **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
- **Comando de inicio:** `docker compose up -d`
- **Repositorio Maestro:** Es este mismo repositorio.

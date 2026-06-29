# Somnia - Colección Postman

Esta carpeta contiene la colección de Postman utilizada para probar los endpoints del backend de Somnia.

## Archivos

- `Somnia.postman_collection.json`: colección con los endpoints del proyecto.
- `Somnia.postman_environment.json`: variables de entorno para las pruebas, si aplica.
- `Somnia_documentacion_postman.txt`: resumen de los endpoints.

## Requisitos

Antes de probar la colección, se debe tener:

- El backend de Somnia en ejecución.
- La base de datos MySQL activa.
- El proyecto corriendo en `http://localhost:8080`.

## Cómo usarlo

1. Abrir Postman.
2. Dar clic en `Import`.
3. Importar la colección `Somnia.postman_collection.json`.
4. Importar el environment si está disponible.
5. Ejecutar los endpoints según el módulo que se quiera probar.

## Orden recomendado

1. Registrar usuario administrador.
2. Iniciar sesión como administrador.
3. Registrar usuario estudiante.
4. Iniciar sesión como estudiante.
5. Probar usuarios, objetivos, registros de sueño e historial.

## Módulos

- **Auth:** registro e inicio de sesión.
- **Users:** gestión de usuarios.
- **Sleep Goals:** objetivos de descanso.
- **Sleep Records:** registros de sueño.
- **History:** historial y seguimiento.

## Nota

Algunos endpoints usan Basic Auth, por lo que se debe ingresar el correo y contraseña del usuario registrado para probarlos correctamente.
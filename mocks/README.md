### Mocks y Dummies del MVP

- Propósito:
  - Permitir pruebas tempranas de flujo (`ui -> domain -> data`) antes de integrar servicios reales.
- Regla:
  - Estos datos son determinísticos y no deben contener secretos, PII real ni tokens.

#### Estructura

- `mocks/auth/`: respuestas de autenticación y perfil inicial.
- `mocks/users_profile/`: membresías, familias y cambios de rol simulados.
- `mocks/classes_attendance/`: asistencias offline/sync para probar idempotencia.
- `mocks/routines/`: listado y creación de rutinas simuladas.
- `mocks/dashboard_admin/`: métricas y notificaciones simuladas.

#### Contrato de uso

- La capa `ui` no debe leer estos archivos directamente.
- Se consumen desde implementaciones fake de repositorio en `data`.
- El switch fake/real debe hacerse por DI y configuración de entorno.

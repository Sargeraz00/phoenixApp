### Plan de Entregables — PhoenixApp (MVP Android + iOS)

Este roadmap divide el proyecto en entregables estrictos, secuenciales y auditables, alineados al `guidelines.md`.

---

#### Entregable 0 — Base de plataforma y build estable

- Objetivo:
  - Dejar el proyecto compilando de forma consistente con versión de dependencias centralizada y targets del MVP definidos.
- Entregables visibles:
  - `gradle/libs.versions.toml` con dependencias oficiales base del stack.
  - `build.gradle.kts` y/o módulos actualizados para soportar el setup inicial.
  - Documento breve de decisión de versiones (en este archivo o README técnico).
- Nota backend/database:
  - Backend: preparar entradas de versión para GitLive Firebase SDK y Ktor.
  - Database: preparar entradas de versión para SQLDelight + drivers Android/iOS.
- Definition of Done:
  - Build base sin errores.
  - Version Catalog como fuente única de versiones.

#### Entregable 1 — Estructura de arquitectura por capas y features

- Objetivo:
  - Crear la base de directorios de `features/<nombre>` y capas `ui -> domain -> data`.
- Entregables visibles:
  - Árbol inicial de carpetas para:
    - `features/auth`
    - `features/access_qr`
    - `features/users_profile`
    - `features/classes_attendance`
    - `features/routines`
    - `features/dashboard_admin`
  - Carpetas técnicas transversales:
    - `commons/`
    - `utils/`
    - `di/`
    - `network/`
    - `database/`
- Nota backend/database:
  - Backend: `network/firebase` reservado para integración GitLive.
  - Database: `database/schema` y `database/migrations` reservados para SQLDelight.
- Definition of Done:
  - Estructura creada y visible en repo.
  - Naming consistente con guidelines.

#### Entregable 2 — Contratos transversales de dominio

- Objetivo:
  - Estandarizar los contratos base antes de lógica de negocio.
- Entregables visibles:
  - `AppResult` y `AppError` en dominio común.
  - Convenciones base de mapeo (`toDomain`, `toDto`, `toUiModel`).
  - Contratos iniciales de repositorio por feature (interfaces vacías o mínimas).
- Nota backend/database:
  - Backend: todos los errores externos deben mapear a `AppError` en `data`.
  - Database: errores de SQLDelight también deben mapearse en `data`.
- Definition of Done:
  - Ningún contrato de `domain` depende de Firebase, Ktor, SQLDelight o Compose.

#### Entregable 3 — DI modular y utilidades base

- Objetivo:
  - Habilitar inyección por módulos para escalar por feature.
- Entregables visibles:
  - `di/AppModules.kt` y `di/FeatureModules.kt`.
  - Módulos iniciales: `appModule`, `networkModule`, `databaseModule`, `authModule`.
  - `DispatcherProvider` y bindings base.
- Nota backend/database:
  - Backend: clientes Firebase/Ktor declarados como `single`.
  - Database: driver/DB de SQLDelight declarado como `single`.
- Definition of Done:
  - Grafo de DI inicial resolviendo sin ciclos.

#### Entregable 4 — Base de backend as a service (Google/Firebase)

- Objetivo:
  - Dejar lista la integración segura y encapsulada de BaaS.
- Entregables visibles:
  - Setup de SDK compartido de Firebase (GitLive).
  - Esqueleto de data sources `remote` para `auth` y `users_profile`.
  - Documento de reglas mínimas de seguridad (role, nextPaymentDate, attendances append-only).
  - Runbook de bootstrap de `ADMIN` (fuera de app).
- Nota backend/database:
  - Backend: Cloud Function/Claims para promoción de roles + auditoría en `audit_logs`.
  - Database: no aplica persistencia local completa aquí, solo contrato para sincronización.
- Definition of Done:
  - Firebase solo referenciado desde `data`.
  - Reglas de rol documentadas.

#### Entregable 5 — Persistencia local y estrategia offline-first

- Objetivo:
  - Implementar base local para asistencias y soporte de sync.
- Entregables visibles:
  - Setup SQLDelight (schema inicial + migración inicial).
  - Modelo append-only de asistencias con UUID idempotente.
  - Marcador `delayed_sync` para registros fuera de ventana.
- Nota backend/database:
  - Backend: upsert por mismo UUID para idempotencia en reintentos.
  - Database: tablas y migraciones versionadas desde día 1.
- Definition of Done:
  - Inserción local y lectura funcionando.
  - Contrato listo para sincronización segura.

#### Entregable 6 — Mocks/dummies para validar flujo antes de servicios reales

- Objetivo:
  - Probar navegación/estado/casos de uso sin depender de red real.
- Entregables visibles:
  - Carpeta `mocks/` con datasets por feature y contrato de uso.
  - Repositorios fake/dummy (cuando se implementen features) conectados vía DI para modo demo.
  - Escenarios mínimos: login, rol, membresía, asistencia, rutinas.
- Nota backend/database:
  - Backend: mocks deben representar respuestas de Auth/Firestore/Functions esperadas.
  - Database: mocks deben incluir casos de offline pendiente, sincronizado y fallido.
- Definition of Done:
  - Flujo end-to-end navegable en modo mock.
  - Cambio entre fuentes fake/real controlado por configuración.

#### Entregable 7 — Observabilidad, secretos y calidad

- Objetivo:
  - Blindar operación, seguridad y calidad antes de escalar features.
- Entregables visibles:
  - Logging unificado con Kermit + sanitización de PII/tokens.
  - Correlation IDs en flujos críticos (`loginAttemptId`, `syncSessionId`).
  - Manejo de secretos con BuildKonfig/local properties por entorno (`dev`, `stage`, `prod`).
  - Gates de calidad: `ktlintCheck`, `detekt`, `testDebugUnitTest`, `assembleDebug`.
- Nota backend/database:
  - Backend: no loggear seed TOTP ni credenciales.
  - Database: logs de sync sin datos sensibles de usuarios.
- Definition of Done:
  - Pipeline de calidad en verde.
  - Sin secretos en repositorio.

#### Entregable 8 — Feature slices (implementación incremental)

- Objetivo:
  - Implementar verticalmente feature por feature con riesgo controlado.
- Orden recomendado:
  1. `auth`
  2. `users_profile`
  3. `classes_attendance`
  4. `access_qr`
  5. `routines`
  6. `dashboard_admin`
- Nota backend/database:
  - Cada feature debe cerrar su contrato backend/database antes de pasar a la siguiente.
- Definition of Done:
  - Cada slice entrega: UI + domain + data + pruebas + reglas/contratos validados.

---

#### Criterio operativo de entregas

- No se inicia un entregable nuevo sin cerrar el anterior (DoD cumplido).
- Cada PR debe indicar explícitamente: `Entregable X`, alcance y evidencia.
- Si surge una necesidad fuera del marco, primero se actualiza `guidelines.md` y luego se implementa.

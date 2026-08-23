# Lineamientos Técnicos Oficiales — PhoenixApp KMP/CMP

Este documento es la **fuente única de verdad** para el desarrollo del proyecto. Todas las reglas aquí definidas son **obligatorias**.

---

## 1) Convenciones de Nombrado (Naming)

### 1.1 Archivos y clases por tipo

Reglas obligatorias:
- Pantallas de feature: `*Screen.kt` y función principal `*Screen`.
- Contenido desacoplado/reutilizable de pantalla: `*Content.kt` y función `*Content`.
- ViewModel: `*ViewModel.kt` y clase `*ViewModel`.
- Estado UI: `*UiState.kt` y `data class *UiState`.
- Eventos UI (one-shot): `*Event.kt` y `sealed interface *Event`.
- Actions/Intents de UI: `*Action.kt` y `sealed interface *Action`.
- UseCase: `*UseCase.kt` y clase `*UseCase`.
- Repositorio interfaz (domain): `*Repository.kt`.
- Repositorio implementación (data): `*RepositoryImpl.kt`.
- DTOs de red/Firebase: sufijo `Dto` (`UserDto`, `MembershipDto`).
- Modelos de dominio: sin sufijo técnico (ej. `User`, `Membership`).
- Modelos de UI: sufijo `UiModel` (`UserUiModel`).

Ejemplo correcto:
```kotlin
// features/auth/ui/LoginScreen.kt
@Composable
fun LoginScreen(...) { /* ... */ }

// features/auth/ui/LoginViewModel.kt
class LoginViewModel(...) : ViewModel()

// features/auth/domain/LoginUseCase.kt
class LoginUseCase(...)

// features/auth/domain/AuthRepository.kt
interface AuthRepository

// features/auth/data/AuthRepositoryImpl.kt
class AuthRepositoryImpl(...) : AuthRepository
```

Contraejemplo (NO hacer):
```kotlin
// user_interface.kt
class loginVM
class DoLogin
interface IAuthRepo
class AuthRepo
```

### 1.2 Sufijos/prefijos obligatorios

Reglas obligatorias:
- Estado observable de pantalla: `XxxUiState`.
- Eventos one-shot: `XxxEvent`.
- Intenciones/acciones del usuario: `XxxAction`.
- Implementaciones concretas: sufijo `Impl`.
- Interfaces de repositorio: sufijo `Repository` (sin prefijo `I`).
- Mappers: `toDomain`, `toDto`, `toUiModel`.

Ejemplo correcto:
```kotlin
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data class ShowError(val message: String) : LoginEvent
}

sealed interface LoginAction {
    data class EmailChanged(val value: String) : LoginAction
    data class PasswordChanged(val value: String) : LoginAction
    data object Submit : LoginAction
}
```

Contraejemplo (NO hacer):
```kotlin
data class State(...)
sealed interface Event(...)
interface IAuthRepository
class AuthRepositoryConcrete
```

### 1.3 Funciones Composable

Reglas obligatorias:
- Funciones `@Composable` en **PascalCase**.
- Una pantalla de navegación SIEMPRE usa sufijo `Screen`.
- Componente reutilizable NO debe usar sufijo `Screen`; usar nombre semántico (`LoginForm`, `PrimaryButton`).
- `Screen` coordina estado/navegación; `Content` renderiza UI pura.

Ejemplo correcto:
```kotlin
@Composable
fun LoginScreen(viewModel: LoginViewModel, modifier: Modifier = Modifier) { /* ... */ }

@Composable
fun LoginContent(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) { /* ... */ }

@Composable
fun MembershipCard(modifier: Modifier = Modifier) { /* ... */ }
```

Contraejemplo (NO hacer):
```kotlin
@Composable
fun login_screen() { /* ... */ }

@Composable
fun ReusableWidgetScreen() { /* ... */ }
```

### 1.4 Nombres de paquetes

Reglas obligatorias:
- Todo en minúsculas.
- Sin guiones ni camelCase en segmentos de paquete.
- Estructura alineada al árbol fijo del proyecto.

Ejemplo correcto:
```kotlin
package com.sargedev.phoenixapp.features.auth.ui
package com.sargedev.phoenixapp.features.memberships.data.remote
```

Contraejemplo (NO hacer):
```kotlin
package com.sargedev.PhoenixApp.Features.Auth.UI
package com.sargedev.phoenix_app.features.auth
```

### 1.5 Constantes, colecciones Firestore y claves de almacenamiento

Reglas obligatorias:
- Constantes globales: `UPPER_SNAKE_CASE`.
- Colecciones Firestore: `snake_case` en plural (`users`, `memberships`, `attendance_logs`).
- Claves DataStore/SharedPreferences: prefijo por dominio (`auth_`, `user_`, `settings_`).

Ejemplo correcto:
```kotlin
const val COLLECTION_USERS = "users"
const val COLLECTION_MEMBERSHIPS = "memberships"
const val KEY_AUTH_ACCESS_TOKEN = "auth_access_token"
const val KEY_SETTINGS_DARK_MODE = "settings_dark_mode"
```

Contraejemplo (NO hacer):
```kotlin
const val usersCollection = "Users"
const val token = "token"
```

---

## 2) Arquitectura y Capas

### 2.1 Dependencias entre capas

Reglas obligatorias:
- Flujo único permitido: `ui -> domain -> data`.
- `ui` **nunca** importa DTOs ni SDK Firebase.
- `domain` no depende de frameworks (Firebase, SQLDelight, Compose).
- `data` implementa interfaces de `domain`.
- Una feature no accede a implementación interna de otra feature.

Ejemplo correcto:
```kotlin
// ui
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel()

// domain
class LoginUseCase(
    private val repository: AuthRepository
)

// data
class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository
```

Contraejemplo (NO hacer):
```kotlin
// ui usando firebase directamente
class LoginViewModel : ViewModel() {
    fun login() {
        Firebase.auth.signInWithEmailAndPassword("a", "b")
    }
}
```

### 2.2 UseCase estándar

Reglas obligatorias:
- Un solo propósito por caso de uso.
- Exponer `operator fun invoke(...)`.
- Retornar `Result<T>` o tipo de error de dominio.

Ejemplo correcto:
```kotlin
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}
```

Contraejemplo (NO hacer):
```kotlin
class AuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun loginAndFetchProfileAndSync(...) { /* demasiadas responsabilidades */ }
}
```

### 2.3 Repository estándar

Reglas obligatorias:
- Interfaz en `domain/repository`.
- Implementación en `data/repository`.
- Firma de interfaz define contrato de errores/resultado.

Ejemplo correcto:
```kotlin
// domain
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}

// data
class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource,
    private val local: AuthLocalDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return remote.login(email, password)
    }
}
```

### 2.4 Manejo de errores

Reglas obligatorias:
- En capas superiores (`ui`/`domain`) está prohibido usar excepciones para control de flujo.
- Usar `Result<T>` o `sealed interface AppError`.
- Captura de excepciones solo en `data` (boundary con SDK/red/db) y mapear a errores de dominio.

Ejemplo correcto:
```kotlin
sealed interface AppError {
    data object Network : AppError
    data object Unauthorized : AppError
    data object Unknown : AppError
}

typealias AppResult<T> = Result<T>

fun Throwable.toAppError(): AppError = when (this) {
    is IllegalStateException -> AppError.Unauthorized
    else -> AppError.Unknown
}
```

Contraejemplo (NO hacer):
```kotlin
// domain
fun execute(): User {
    throw RuntimeException("invalid credentials")
}
```

### 2.5 Mappers DTO -> Domain -> UI

Reglas obligatorias:
- `data`: `Dto <-> Domain`.
- `ui`: `Domain -> UiModel` cuando sea necesario para presentación.
- No mapear DTO directamente en UI.

Ejemplo correcto:
```kotlin
// data/mapper
data class UserDto(val id: String, val full_name: String)
data class User(val id: String, val fullName: String)

fun UserDto.toDomain(): User = User(
    id = id,
    fullName = full_name
)

// ui/mapper
data class UserUiModel(val title: String)

fun User.toUiModel(): UserUiModel = UserUiModel(title = fullName)
```

Contraejemplo (NO hacer):
```kotlin
// ui usando dto
@Composable
fun ProfileScreen(userDto: UserDto) { /* ... */ }
```

---

## 3) Estilo Kotlin

### 3.1 `data class` vs `class` e inmutabilidad

Reglas obligatorias:
- `data class` para modelos de estado/transferencia.
- `class` para servicios, casos de uso y coordinadores.
- Inmutabilidad por defecto: usar `val`; `var` solo con justificación funcional.

Ejemplo correcto:
```kotlin
data class Membership(
    val id: String,
    val planName: String,
    val active: Boolean
)

class GetMembershipUseCase(...)
```

### 3.2 Null-safety

Reglas obligatorias:
- `!!` está prohibido.
- Usar `?.`, `?:`, `requireNotNull`, `checkNotNull` con mensaje explícito.

Ejemplo correcto:
```kotlin
fun requireUserId(userId: String?): String {
    return requireNotNull(userId) { "userId no puede ser null" }
}
```

Contraejemplo (NO hacer):
```kotlin
val id = userId!!
```

### 3.3 Scope functions

Reglas obligatorias:
- `let`: transformación/null chaining.
- `run`: calcular resultado con receptor.
- `apply`: configurar objeto y devolver el mismo objeto.
- `also`: side-effects (logging/tracking) sin romper cadena.

Ejemplo correcto:
```kotlin
val title = user?.let { "Hola, ${it.fullName}" } ?: "Invitado"

val request = LoginRequest().apply {
    email = "user@mail.com"
    password = "secret"
}

repository.login(email, password)
    .also { result -> logger.log(result) }
```

### 3.4 Estructura de archivo Kotlin

Reglas obligatorias (orden):
1. `package`
2. `imports`
3. constantes top-level relacionadas
4. declaración de clase
5. propiedades
6. `init`
7. funciones públicas
8. funciones privadas

Ejemplo correcto:
```kotlin
package com.sargedev.phoenixapp.features.auth.ui

import androidx.lifecycle.ViewModel

private const val MIN_PASSWORD_LENGTH = 8

class LoginViewModel(...) : ViewModel() {
    private val state = ...

    init {
        // setup
    }

    fun onAction(action: LoginAction) { /* ... */ }

    private fun validate(password: String): Boolean = password.length >= MIN_PASSWORD_LENGTH
}
```

### 3.5 Coroutines

Reglas obligatorias:
- En `ViewModel`, lanzar trabajos en `viewModelScope`.
- `Dispatchers` se inyectan mediante proveedor/abstracción; no hardcodear `Dispatchers.IO` en casos de uso/repos.
- Respetar cancelación (`CancellationException` se relanza).

Ejemplo correcto:
```kotlin
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}

suspend fun <T> runIo(
    dispatcherProvider: DispatcherProvider,
    block: suspend () -> T
): T = withContext(dispatcherProvider.io) {
    block()
}
```

Contraejemplo (NO hacer):
```kotlin
suspend fun fetch(): Data = withContext(Dispatchers.IO) { /* hardcoded */ }
```

---

## 4) Estilo Compose Multiplatform

### 4.1 Estructura Screen vs Content

Reglas obligatorias:
- `XxxScreen`: estado + eventos + navegación.
- `XxxContent`: función pura de render, sin dependencia de ViewModel.

Ejemplo correcto:
```kotlin
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    LoginContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}
```

### 4.2 Estado y eventos

Reglas obligatorias:
- `UiState` como `data class` inmutable.
- One-shot events con `sealed interface` + `SharedFlow`/`Channel`.
- No exponer `MutableStateFlow` fuera de ViewModel.

Ejemplo correcto:
```kotlin
class LoginViewModel(...) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events
}
```

### 4.3 Conexión ViewModel-Composable

Reglas obligatorias:
- Usar `collectAsStateWithLifecycle` cuando esté disponible en la plataforma.
- En commonMain, usar equivalente multiplataforma definido en `commons/` (wrapper propio si aplica).

Ejemplo correcto:
```kotlin
@Composable
fun LoginScreen(...) {
    val state by viewModel.uiState.collectAsState()
    // Reemplazar por collectAsStateWithLifecycle mediante wrapper común cuando esté configurado.
}
```

### 4.4 Convenciones de `Modifier`

Reglas obligatorias:
- `modifier: Modifier = Modifier` siempre como último parámetro opcional en Composables de UI.
- Nunca hardcodear padding externo en componentes base cuando deba decidirlo el caller.

Ejemplo correcto:
```kotlin
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = text)
    }
}
```

### 4.5 Previews

Reglas obligatorias:
- Funciones preview con nombre `XxxPreview`.
- Ubicadas al final del archivo de UI.
- Usar datos mock determinísticos.

Ejemplo correcto:
```kotlin
@Preview
@Composable
private fun LoginContentPreview() {
    LoginContent(
        state = LoginUiState(email = "demo@mail.com"),
        onAction = {}
    )
}
```

### 4.6 Recursos compartidos

Reglas obligatorias:
- Strings, colores y dimensiones compartidos van en `commonMain` (módulos compartidos de recursos/tema).
- Prohibido duplicar literales de UI en múltiples features.

Ejemplo correcto:
```kotlin
object AppDimens {
    val spacingSmall = 8.dp
    val spacingMedium = 16.dp
}
```

---

## 5) Inyección de Dependencias (Koin)

### 5.1 Organización de módulos

Reglas obligatorias:
- Un módulo por feature y por capa técnica transversal.
- Nombres: `authModule`, `dashboardModule`, `networkModule`, `databaseModule`.
- Módulo raíz en `di/` agrega todos los módulos.

Ejemplo correcto:
```kotlin
val authModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}

val appModule = module {
    includes(networkModule, databaseModule, authModule)
}
```

### 5.2 Criterio `single`, `factory`, `viewModel`

Reglas obligatorias:
- `single`: servicios compartidos sin estado efímero (clientes Firebase, DAOs, dispatchers providers).
- `factory`: casos de uso y componentes livianos sin ciclo largo.
- `viewModel`: ViewModels de presentación.

Contraejemplo (NO hacer):
```kotlin
val badModule = module {
    single { LoginViewModel(get()) } // incorrecto
}
```

---

## 6) Estructura de Archivos por Feature

### 6.1 Árbol obligatorio (ejemplo `feat_x`)

```text
composeApp/src/commonMain/kotlin/com/sargedev/phoenixapp/
├── commons/
│   ├── extensions/
│   ├── mappers/
│   ├── theme/
│   └── components/
├── utils/
│   ├── formatters/
│   ├── permissions/
│   └── security/
├── network/
│   ├── firebase/
│   └── client/
├── di/
│   ├── AppModules.kt
│   └── FeatureModules.kt
├── database/
│   ├── driver/
│   ├── schema/
│   └── migrations/
└── features/
    └── feat_x/
        ├── data/
        │   ├── remote/
        │   │   ├── FeatXRemoteDataSource.kt
        │   │   └── dto/
        │   │       └── FeatXDto.kt
        │   ├── local/
        │   │   ├── FeatXLocalDataSource.kt
        │   │   └── entity/
        │   │       └── FeatXEntity.kt
        │   ├── mapper/
        │   │   └── FeatXDataMapper.kt
        │   └── repository/
        │       └── FeatXRepositoryImpl.kt
        ├── domain/
        │   ├── model/
        │   │   └── FeatXModel.kt
        │   ├── repository/
        │   │   └── FeatXRepository.kt
        │   └── usecase/
        │       ├── GetFeatXUseCase.kt
        │       └── UpdateFeatXUseCase.kt
        └── ui/
            ├── screen/
            │   ├── FeatXScreen.kt
            │   └── FeatXContent.kt
            ├── viewmodel/
            │   ├── FeatXViewModel.kt
            │   ├── FeatXUiState.kt
            │   ├── FeatXAction.kt
            │   └── FeatXEvent.kt
            └── mapper/
                └── FeatXUiMapper.kt
```

### 6.2 Criterio de ubicación (`commons` vs `utils` vs feature)

Reglas obligatorias:
- Va en `commons/` si:
  - Es código de presentación compartido por **2 o más features** (componentes Compose, tema, extensiones UI, mappers genéricos de UI).
- Va en `utils/` si:
  - Es utilidad técnica transversal no ligada a un flujo de negocio (TOTP, formatters, permisos, validadores genéricos).
- Va dentro de `features/feat_x/...` si:
  - Está acoplado al dominio/flujo de una sola feature.
- Un mapper usado por 2+ features migra a `commons/mappers`; si solo lo usa una feature, permanece en esa feature.

Ejemplo correcto:
```kotlin
// commons/components/PrimaryButton.kt -> reutilizado por auth, dashboard, members
@Composable
fun PrimaryButton(...) { /* ... */ }

// utils/formatters/CurrencyFormatter.kt -> utilidad transversal
class CurrencyFormatter { /* ... */ }

// features/auth/data/mapper/AuthMapper.kt -> solo auth
fun AuthDto.toDomain(): AuthUser = ...
```

Contraejemplo (NO hacer):
```kotlin
// mapper exclusivo de auth colocado en commons sin reutilización real
package com.sargedev.phoenixapp.commons.mappers
```

---

## Reglas de cumplimiento operativo

- Cualquier PR/cambio que viole estas reglas debe corregirse antes de merge.
- Si una nueva necesidad no está cubierta aquí, se actualiza este documento primero y luego se implementa el código.
- No se permite introducir una excepción local sin registrar la justificación técnica en este archivo.

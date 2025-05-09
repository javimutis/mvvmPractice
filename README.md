# 📱 App de Citas de Programadores - Ejemplo MVVM en Android

Este proyecto es una aplicación sencilla que muestra citas relacionadas con la programación.  
Está desarrollada en **Kotlin** utilizando el patrón de arquitectura **MVVM** (Model - View - ViewModel), principios de **Clean Architecture** e implementa **inyección de dependencias con Dagger Hilt**.  
También incorpora una **base de datos local con Room**, una sección de **favoritos**, y **pruebas unitarias** para verificar la lógica de negocio.

Además, integra un flujo **CI/CD (Integración y Entrega Continua)** con **GitHub Actions**, incluyendo pruebas automáticas en cada push o pull request.


## 👩‍🏫 ¿Qué hace esta app?

✅ Muestra una cita inspiradora aleatoria sobre programación.  
✅ Al hacer clic en la pantalla, carga una nueva cita aleatoria.  
✅ Las citas provienen de Firebase Realtime Database y se guardan localmente con Room.  
✅ Funciona sin conexión usando la base local.  
✅ Los usuarios pueden marcar frases como favoritas ❤️.  
✅ Muestra todas las frases favoritas en una nueva pantalla.  
✅ Corre pruebas unitarias automáticamente usando CI/CD con GitHub Actions.

![Demo de la App](assets/demo.gif)

## 🧠 Arquitectura MVVM + Clean (simplificada)

La app está dividida en capas para mantener el código ordenado, entendible y escalable:

### 🧱 Modelo (Model)
Representa los datos y su origen.

- `QuoteModel.kt`: Modelo de dominio (cita con texto,  autor, favorito).
- `QuoteEntity.kt`: Entidad Room para persistencia local.

### 🌐 Red (Network)
Encargada de comunicarse con la API.

- `QuoteApiClient.kt`: Define el endpoint para obtener todas las citas desde Firebase usando Retrofit.
- `QuoteService.kt`: Ejecuta la llamada a la API utilizando `RetrofitHelper`.

### 🗄️ Base de datos local (Room)
Permite guardar y recuperar citas localmente.

- `QuoteDao.kt`:
  - Consultas para obtener, insertar, actualizar y filtrar por favoritos.
  - Nuevo método `getFavoriteQuotes()` para obtener solo favoritas.
- `QuoteDatabase.kt`: Configuración de Room.

### 📦 Repositorio (Repository)
Intermediario entre los datos (API / Room) y la lógica de negocio (Use Cases).

-  `QuoteRepository.kt`:
- Obtiene citas desde la API.
- Guarda y actualiza citas en la base local.
- Maneja el estado de favoritas.
- Devuelve citas favoritas como `Flow<List<Quote>>`.

### 🎯 Casos de Uso (UseCase)
Contienen la lógica del negocio de la app.

- `GetQuotesUseCase.kt`: Obtiene citas de la API y las guarda localmente.
- `GetRandomQuoteUseCase.kt`: Selecciona una cita aleatoria.
- `SetFavoriteQuoteUseCase.kt`: Marca/desmarca citas como favoritas.
- `GetFavoriteQuoteUseCase.kt`: Obtiene citas favoritas desde Room.

### 👁️ Vista (View)
Se encarga de mostrar los datos al usuario y responder a sus interacciones.

- `MainActivity.kt`:
  - Contiene el host para `QuoteFragment`.
- `QuoteFragment.kt`:
  - Muestra la cita actual y permite marcar como favorita.
- `FavoriteQuotesFragment.kt`:
  - Nueva vista para mostrar todas las frases favoritas usando un RecyclerView.
- `FavoriteQuotesAdapter.kt`: Adapter para listar las favoritas.
- 
### 🧠 ViewModel
Conecta la vista con los datos y la lógica de negocio.

- `QuoteViewModel.kt`:
  - Mantiene el estado de la cita actual y si es favorita.
  - Expone funciones para cambiar cita, marcar favorito y observar favoritos.
- `FavoriteQuotesViewModel.kt`:
  - Obtiene y expone el listado de favoritos como `LiveData`.
  
## 🧪 Pruebas Unitarias

Este proyecto incluye pruebas unitarias que validan el comportamiento de los casos de uso principales:

### ✅ `GetQuotesUseCaseTest.kt`

- **Caso 1:** Si la API no retorna citas, se obtienen desde la base local.
- **Caso 2:** Si la API retorna citas, se borran las anteriores, se guardan las nuevas y se retornan.

### ✅ `GetRandomQuoteUseCaseTest.kt`

- **Caso 1:** Si la base de datos está vacía, retorna `null`.
- **Caso 2:** Si la base tiene citas, retorna una aleatoria.

### ✅ `SetFavoriteQuoteUseCaseTest.kt`

- Marca una cita como favorita correctamente.
- Desmarca una cita favorita correctamente.
- Verifica que el estado guardado se respete.

### ✅ `GetFavoriteQuoteUseCaseTest.kt`
- Obtiene correctamente las citas favoritas como un `Flow<List<Quote>>`.
- Verifica que solo las citas marcadas con `isFavorite = true` son emitidas.

Las pruebas están escritas usando **MockK** y se ejecutan con **coroutines**.

---

## ⚙️ CI/CD y Testing Automático

El proyecto incluye un pipeline de **GitHub Actions** que:

- Se ejecuta automáticamente al hacer **push** o abrir un **pull request** en las ramas `ci/cd` y `master`.
- Construye el proyecto con Gradle.
- Corre las pruebas unitarias (`./gradlew test`).

Esto asegura que el proyecto se mantenga **estable**, que el código enviado pase las pruebas, y que cualquier fallo sea detectado temprano.

---
## 🧩 Inyección de dependencias con Dagger Hilt

La app usa **Dagger Hilt** para:
- Inyectar Retrofit, Room y otros servicios.
- Proveer dependencias al ViewModel.
- Facilitar pruebas y reducir código repetitivo.

Principales anotaciones:
- `@HiltAndroidApp`: Aplicación base configurada para usar Hilt.
- `@Inject`: Se usa para proveer dependencias en clases como `QuoteViewModel`, `QuoteRepository`, y `QuoteService`.
- `@Module` y `@InstallIn`: Se definen módulos para proveer Retrofit, Room, el cliente de API y otras dependencias.

Gracias a Hilt, las dependencias se inyectan automáticamente en el ViewModel y otras capas del proyecto, reduciendo el boilerplate y facilitando el mantenimiento.

## 📡 Backend utilizado

- **Firebase Realtime Database**: Se utiliza como backend para guardar y obtener las citas en formato JSON.

Ejemplo de URL de la base de datos:  
`https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/.json`

## 🧰 Tecnologías utilizadas

- **Kotlin**
- **MVVM**
- **Clean Architecture (simplificada)**
- **Room** como base de datos local
- **Dagger Hilt** para inyección de dependencias
- **ViewModel + LiveData**
- **ViewBinding**
- **Coroutines** para llamadas asíncronas
- **Retrofit** para conectarse a la API
- **Firebase Realtime Database** como fuente de datos
- **MockK** para test unitarios
- **GitHub Actions (CI/CD)** 

## 📖 ¿Para qué sirve este proyecto?

Este proyecto es un excelente punto de partida para:

- Aprender a implementar el patrón MVVM en Android.
- Comprender el flujo completo de datos desde una API hasta la interfaz.
- Practicar con Retrofit, Firebase, Coroutines y Room.
- Introducirse en Clean Architecture y buenas prácticas.
- Aprender a implementar **inyección de dependencias con Hilt**.
- Familiarizarse con `ViewBinding`, `LiveData`, `ViewModel` y más.
- Practicar pruebas unitarias con casos de uso reales.
- Ampliar funcionalidades.
- Configurar CI/CD con GitHub Actions.

## 🛠️ ¿Cómo correr este proyecto?

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Asegúrate de tener conexión a internet (las citas se cargan desde Firebase).
4. Ejecuta la app en un emulador o dispositivo físico.
5. La primera vez se cargan las citas desde la API. Luego, todo funciona desde la base de datos local.

---

✨ Este proyecto está pensado como material de estudio y base para futuros desarrollos. Puedes expandirlo agregando navegación entre pantallas, guardar citas favoritas, paginación, pruebas unitarias, compartir citas, entre otros.

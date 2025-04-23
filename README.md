# 📱 App de Citas de Programadores - Ejemplo MVVM en Android

Este proyecto es una aplicación sencilla que muestra citas relacionadas con la programación. Está desarrollada en **Kotlin** utilizando el patrón de arquitectura **MVVM** (Model - View - ViewModel), principios de **Clean Architecture**, e implementa **inyección de dependencias con Dagger Hilt**.

## 👩‍🏫 ¿Qué hace esta app?

- Al abrir la app, muestra una cita inspiradora aleatoria sobre programación.
- Al hacer clic en cualquier parte de la pantalla, se muestra una nueva cita aleatoria.
- Las citas provienen de una base de datos en línea (Firebase Realtime Database).

## 🧠 Arquitectura MVVM + Clean (simplificada)

La app está dividida en capas para mantener el código ordenado, entendible y escalable:

### 🧱 Modelo (Model)
Representa los datos y su origen (API en este caso).

- `QuoteModel.kt`: Modelo de datos que representa una cita (texto + autor).
- `QuoteProvider.kt`: Contenedor temporal de citas (almacena la lista desde la API).

### 🌐 Red (Network)
Encargada de comunicarse con la API.

- `QuoteApiClient.kt`: Define el endpoint para obtener todas las citas desde Firebase usando Retrofit.
- `QuoteService.kt`: Ejecuta la llamada a la API utilizando `RetrofitHelper`.

### 📦 Repositorio (Repository)
Intermediario entre los datos (API) y la lógica de negocio (Use Cases).

- `QuoteRepository.kt`: Obtiene las citas desde el servicio de red y las guarda en el `QuoteProvider`.

### 🎯 Casos de Uso (UseCase)
Contienen la lógica del negocio de la app (una capa opcional pero buena práctica).

- `GetQuoteUseCase.kt`: Obtiene todas las citas desde el repositorio.
- `GetRandomQuoteUseCase.kt`: Elige una cita aleatoria desde la lista cargada.

### 👁️ Vista (View)
Se encarga de mostrar los datos al usuario y responder a sus interacciones.

- `MainActivity.kt`: Muestra la interfaz de usuario, observa los cambios del ViewModel y responde al clic del usuario para mostrar una nueva cita.

### 🧠 ViewModel
Conecta la vista con los datos y la lógica de negocio.

- `QuoteViewModel.kt`: Se comunica con los casos de uso, mantiene el estado de la cita actual y la muestra a la vista usando `LiveData`.

## 🧩 Inyección de dependencias con Dagger Hilt

La app utiliza **Dagger Hilt** para gestionar la inyección de dependencias de forma eficiente y desacoplada. Esto permite una mejor escalabilidad y testeo del código.

- `@HiltAndroidApp`: Aplicación base configurada para usar Hilt.
- `@Inject`: Se usa para proveer dependencias en clases como `QuoteViewModel`, `QuoteRepository`, y `QuoteService`.
- `@Module` y `@InstallIn`: Se definen módulos para proveer Retrofit, el cliente de API y otras dependencias.

Gracias a Hilt, las dependencias se inyectan automáticamente en el ViewModel y otras capas del proyecto, reduciendo el boilerplate y facilitando el mantenimiento.

## 📡 Backend utilizado

- **Firebase Realtime Database**: Se utiliza como backend para guardar y obtener las citas en formato JSON.

Ejemplo de URL de la base de datos:  
`https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/.json`

## 🧰 Tecnologías utilizadas

- **Kotlin**
- **MVVM**
- **Clean Architecture (simplificada)**
- **Dagger Hilt** para inyección de dependencias
- **ViewModel + LiveData**
- **ViewBinding**
- **Coroutines** para llamadas asíncronas
- **Retrofit** para conectarse a la API
- **Firebase Realtime Database** como fuente de datos

## 📖 ¿Para qué sirve este proyecto?

Este proyecto es un excelente punto de partida para:

- Aprender a implementar el patrón MVVM en Android.
- Comprender el flujo completo de datos desde una API hasta la interfaz.
- Practicar con Retrofit, Firebase y Coroutines.
- Introducirse en Clean Architecture y buenas prácticas.
- Aprender a implementar **inyección de dependencias con Hilt**.
- Familiarizarse con `ViewBinding`, `LiveData`, `ViewModel` y más.

## 🛠️ ¿Cómo correr este proyecto?

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Asegúrate de tener conexión a internet (las citas se cargan desde Firebase).
4. Ejecuta la app en un emulador o dispositivo físico.

---

✨ Este proyecto está pensado como material de estudio y base para futuros desarrollos. Puedes expandirlo agregando una base de datos local, navegación entre pantallas, guardar citas favoritas, paginación, pruebas unitarias, entre otros.

# 📱 App de Citas de Programadores- Ejemplo MVVM en Android

Este proyecto es una aplicación muy simple que muestra citas famosas relacionadas con la programación. Está construida con el patrón de arquitectura **MVVM** (Model - View - ViewModel) y utiliza tecnologías modernas como **ViewBinding** y **LiveData**.

## 👩‍🏫 ¿Qué hace esta app?

- Muestra una cita aleatoria al abrir la app.
- Al hacer clic en cualquier parte de la pantalla, se muestra una nueva cita.

## 🧱 Estructura del Proyecto

### Modelo (Model)
- `QuoteModel.kt`: Representa una cita (texto y autor).
- `QuoteProvider.kt`: Contiene una lista de citas y una función para obtener una aleatoriamente.

### Vista (View)
- `MainActivity.kt`: Muestra la interfaz y observa los cambios en las citas para actualizarlas en pantalla.

### ViewModel
- `QuoteViewModel.kt`: Contiene la lógica para seleccionar una nueva cita y notificar a la vista cuando cambia.

## 🧰 Tecnologías utilizadas

- **Kotlin**
- **MVVM**
- **ViewModel**
- **LiveData**
- **ViewBinding**

## 💡 ¿Para qué sirve este proyecto?

Este proyecto está pensado como una base o ejemplo para:
- Aprender a usar MVVM en Android.
- Entender cómo funciona `LiveData` y `ViewModel`.
- Practicar el uso de `ViewBinding`.


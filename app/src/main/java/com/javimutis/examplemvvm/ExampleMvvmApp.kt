package com.javimutis.examplemvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Esta clase es el punto de inicio de la app.
// Le dice a Hilt que puede empezar a inyectar dependencias.
@HiltAndroidApp
class ExampleMvvmApp: Application()

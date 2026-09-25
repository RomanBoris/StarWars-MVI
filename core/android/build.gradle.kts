plugins {
    alias(libs.plugins.android.library)
}

// Android SDK нужен только ради android.app.Application (ApplicationExt.kt) —
// это единственная причина, почему этот код не может лежать в чистом ":core".
// Модуль не знает ни про одну фичу: подключают его и ":app", и любой ":heroes:*"/будущий feature-модуль.
android {
    namespace = "com.pobezhkin.starwars_mvi.core.android"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(project(":core"))
}

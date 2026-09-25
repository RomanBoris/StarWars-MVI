plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.jvm) apply false
}

android {
    namespace = "com.pobezhkin.starwars_mvi"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.pobezhkin.starwars_mvi"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    //Модули
    implementation(project(":core"))
    implementation(project(":core:android"))
    implementation(project(":heroes:impl"))

    // DI
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Fragment-хост для Compose
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    // NetworkModule/DatabaseModule — сеть и база собираются здесь, а не в :heroes:impl:
    // retrofit сам приходит транзитивно из :core (там объявлен как api), а вот
    // OkHttp/конвертер/Room-рантайм нужны здесь напрямую.
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
}
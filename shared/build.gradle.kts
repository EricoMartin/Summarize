import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "2.0.0"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-cio:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            implementation("io.insert-koin:koin-core:3.2.0")
            implementation("io.insert-koin:koin-core:3.5.0")


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.6")
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
            implementation("androidx.activity:activity-compose:1.8.0")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

            // ✅ Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.6")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.basebox.summarize"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        val apiKey = project.loadLocalProperty(
            path = "local.properties",
            propertyName = "apiKey",
        )
        buildConfigField("String", "apiKey", "\"apiKey\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
}

fun Project.loadLocalProperty(
    path: String,
    propertyName: String,
): String {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file(path)
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
        return localProperties.getProperty(propertyName)
    } else {
        throw GradleException("can not find property : $propertyName")
    }

}

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()

    compilerOptions {
        // Enable Kotlin 2.3.0 for experimental features
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)

        // Enable experimental features including data-flow-based exhaustiveness
        freeCompilerArgs.addAll("-Xdata-flow-based-exhaustiveness")


        freeCompilerArgs.addAll("-Xallow-reified-type-in-catch")
    }

    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Add Kotlinx serialization step2 for using type safe navigation
            implementation(libs.kotlinx.serialization.json)

            //for using type safe navigation
            implementation(libs.navigation.compose)

            // use api since the desktop app need to access the Cef to initialize it.
            api("io.github.kevinnzou:compose-webview-multiplatform:2.0.3")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.sunildhiman90.cmplearnings"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sunildhiman90.cmplearnings"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.sunildhiman90.cmplearnings.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.sunildhiman90.cmplearnings"
            packageVersion = "1.0.0"
        }
    }
}

afterEvaluate {
    tasks.withType<JavaExec> {
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED")

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
    }
}

buildkonfig {
    packageName = "com.sunildhiman90.cmplearnings"

    // Load secret.properties
    val secretProps = Properties()
    val secretPropsFile = rootProject.file("secret.properties")
    if (secretPropsFile.exists()) {
        secretProps.load(secretPropsFile.inputStream())
    }

    // default config is required
    defaultConfigs {
        buildConfigField(
            STRING,
            "GEMINI_API_KEY",
            System.getenv("GEMINI_API_KEY") ?: secretProps.getProperty("GEMINI_API_KEY")
        )
        buildConfigField(
            STRING,
            "GOOGLE_API_KEY",
            System.getenv("GEMINI_API_KEY") ?: secretProps.getProperty("GOOGLE_API_KEY")
        )
        buildConfigField(
            STRING,
            "OPENAI_API_KEY",
            System.getenv("OPENAI_API_KEY") ?: secretProps.getProperty("OPENAI_API_KEY")
        )
    }
}
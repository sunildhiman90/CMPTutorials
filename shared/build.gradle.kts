import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    android {
        namespace = "com.sunildhiman90.cmplearnings.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        androidResources { enable = true }
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

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()

    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
        freeCompilerArgs.addAll("-Xdata-flow-based-exhaustiveness")
        freeCompilerArgs.addAll("-Xallow-reified-type-in-catch")
    }

    sourceSets {
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
            implementation(libs.kotlinx.serialization.json)
            api(libs.navigation.compose)
            api("io.github.kevinnzou:compose-webview-multiplatform:2.0.3")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive-layout:1.2.0")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive-navigation:1.2.0")
            implementation(compose.material3AdaptiveNavigationSuite)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

buildkonfig {
    packageName = "com.sunildhiman90.cmplearnings"

    val secretProps = Properties()
    val secretPropsFile = rootProject.file("secret.properties")
    if (secretPropsFile.exists()) {
        secretProps.load(secretPropsFile.inputStream())
    }

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

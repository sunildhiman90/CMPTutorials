package com.sunildhiman90.cmplearnings.kotlin_2_2_20

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * KOTLIN LANGUAGE CHANGES:
 * 1. Improved overload resolution for lambdas with suspend function types
 * 2. Support for return statements in expression bodies with explicit return types
 * 3. experimental -> Data-flow-based exhaustiveness checks for when expressions
 * 4. experimental -> Support for reified types in catch clause.
 * 5. experimental -> Contracts support is extended: We will not discuss this, as its not used that much in normal development.
 *
 * KMP:
 * 1. experimental -> Swift export
 * 2. stable -> shared webMain source set for js and wasmJs
 * 3. experimental -> New approach for declaring common dependencies(just using dependencies block instead of commonMain.dependencies)
 *
 * Kotlin native:
 * 1. experimental -> Smaller binary size for release binaries
 * 2. Deprecation of x86_64 Apple targets
 */
@Composable
fun WhatsNewInKotlin_2_2_20() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("New Features in Kotlin 2.2.20", style = MaterialTheme.typography.headlineMedium)
    }

    //1. Data-flow-based exhaustiveness checks for when expressions
    // We need to include language version in compilerOptions in build.gradle.kts: languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
    // Defines two overloads
    fun transform(block: () -> Int) {}
    fun transform(block: suspend () -> Int) {}
        // Fails with overload resolution ambiguity
    transform({ 42 })

    // Uses an explicit cast, but the compiler incorrectly reports
    // a "No cast needed" warning
    transform({ 42 })
    transform(suspend { 42 })


    //2. Support for return statements in expression bodies with explicit return types,
    // We need to include language version in compilerOptions in build.gradle.kts: languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
    // this wil work only if we have specified return type explicitly
    // it wil throw error: Returns are prohibited for functions with an expression body. Use block body '{...}'. before kotlin 2.2.20
    fun returnInt(): Int = return 1


    //3. Data-flow-based exhaustiveness checks for when expressions
    // We need to include freeCompilerArgs.add("-Xdata-flow-based-exhaustiveness") in compilerOptions in build.gradle.kts
    fun test(role: UserRole): Int {
        // Compiler will know about this data as well in kotlin 2.2.20,
        // but before that compiler was not aware about this data flow, but only about when expression
        if (role == UserRole.ADMIN) return 99

        //This will apply on sealed classes as well
        return when (role) {
            UserRole.MEMBER -> 10
            UserRole.GUEST -> 1
            // We no longer have to include this else branch
            // else -> throw IllegalStateException()
        }
    }

    //4. Support for reified types in catch clause:
    // we need to add freeCompilerArgs.add("-Xallow-reified-type-in-catch") in compilerOptions in build.gradle.kts
    // This will be available by default in kotlin 2.4
    handleException<Exception> {
        throw Exception("Exception")
    }

    //5. Contracts support is extended: We will not discuss this, as its not used that much in normal development.
    //Support for generics in contract type assertions.
    //
    //Support for contracts inside property accessors and specific operator functions.
    //
    //Support for the returnsNotNull() function in contracts as a way to ensure a non-null return value when a condition is met.



}

inline fun <reified ExceptionType : Throwable> handleException(block: () -> Unit) {
    try {
        block()
        // This is now allowed after the change
    } catch (e: ExceptionType) {
        println("Caught specific exception: ${e::class.simpleName}")
    }
}

enum class UserRole { ADMIN, MEMBER, GUEST }







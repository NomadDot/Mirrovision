package com.drowsynomad.mirrovision.presentation.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.drowsynomad.mirrovision.presentation.navigation.Routes
import com.google.gson.Gson
import org.joda.time.LocalDate
import java.util.Locale
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

val GlobalGson = Gson()

inline fun <reified ROUTE : Routes, reified R: Parcelable> NavGraphBuilder.composableOf(
    noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = {
        slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
    },
    noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    ExitTransition?) = {
        slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(500)    )
    },
    noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    EnterTransition?)? = {
        slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
    },
    noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    ExitTransition?)? = {
        slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
    },
    typedMap: Map<KType, NavType<R>> = getTypedMap<R>(),
    noinline content: @Composable AnimatedContentScope.(ROUTE, NavBackStackEntry) -> Unit
) {
    composable<ROUTE>(
        typeMap = typedMap,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popExitTransition = popExitTransition,
        popEnterTransition = popEnterTransition
    ) {
        val args = it.toRoute<ROUTE>()
        content(args, it)
    }
}

inline fun <reified T: Parcelable> getTypedMap() = mapOf(typeOf<T>() to NavTypeFactory.create<T>())

class NavTypeFactory {
    companion object {
        inline fun <reified T: Parcelable> create(): NavType<T> {
            return object : NavType<T>(false) {
                override fun get(bundle: Bundle, key: String): T? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        bundle.getParcelable(key, T::class.java)
                    } else {
                        @Suppress("DEPRECATION") // for backwards compatibility
                        bundle.getParcelable(key)
                    }

                override fun put(bundle: Bundle, key: String, value: T) =
                    bundle.putParcelable(key, value)

                override fun parseValue(value: String): T = value.fromJson()

                override fun serializeAsValue(value: T): String = value.toJson()

                override val name: String = "ScreenInfo"
            }
        }
    }
}

fun Any.toJson(): String = GlobalGson.toJson(this)

inline fun <reified T> String.fromJson(): T =
    GlobalGson.newBuilder().create().fromJson(
        this,
        T::class.java
    )

fun String?.clearRoute(): String = this?.substringAfterLast(".") ?: "$this"

@Composable
@ReadOnlyComposable
fun getLocale(): Locale {
    val configuration = LocalConfiguration.current
    return ConfigurationCompat.getLocales(configuration).get(0) ?: LocaleListCompat.getDefault()[0]!!
}

val calendar: LocalDate by lazy { LocalDate.now() }
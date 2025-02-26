package com.yangian.callsync.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
    surfaceBright = md_theme_light_surfaceBright,
    surfaceDim = md_theme_light_surfaceDim,
    surfaceContainer = md_theme_light_surfaceContainer,
    surfaceContainerHigh = md_theme_light_surfaceContainerHigh,
    surfaceContainerHighest = md_theme_light_surfaceContainerHighest,
    surfaceContainerLow = md_theme_light_surfaceContainerLow,
    surfaceContainerLowest = md_theme_light_surfaceContainerLowest
)

val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
    surfaceBright = md_theme_dark_surfaceBright,
    surfaceDim = md_theme_dark_surfaceDim,
    surfaceContainer = md_theme_dark_surfaceContainer,
    surfaceContainerHigh = md_theme_dark_surfaceContainerHigh,
    surfaceContainerHighest = md_theme_dark_surfaceContainerHighest,
    surfaceContainerLow = md_theme_dark_surfaceContainerLow,
    surfaceContainerLowest = md_theme_dark_surfaceContainerLowest
)

val LightBackgroundTheme = BackgroundTheme(color = md_theme_light_surfaceContainerLow)

val DarkBackgroundTheme = BackgroundTheme(color = md_theme_dark_surfaceContainerHigh)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    val supportsDynamicTheming = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme = when {
        supportsDynamicTheming && useDynamicColors -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (useDarkTheme) DarkColorScheme else LightColorScheme
    }

    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )

    val backgroundTheme = when {
        supportsDynamicTheming -> defaultBackgroundTheme
        else -> if (useDarkTheme) DarkBackgroundTheme else LightBackgroundTheme
    }

    val tintTheme = when {
        supportsDynamicTheming -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }

//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            val insetsController = WindowCompat.getInsetsController(window, view)
//            window.statusBarColor = colorScheme.background.toArgb()
//            window.navigationBarColor = colorScheme.background.toArgb()
//            insetsController.isAppearanceLightStatusBars = !useDarkTheme
//            insetsController.isAppearanceLightNavigationBars = !useDarkTheme
//        }
//    }

    CompositionLocalProvider(
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
//            typography = CallSyncAppTypography,
            content = content
        )
    }
}

@Immutable
data class ExtendedColorScheme(
    val success: ExtendedColorFamily,
)

val extendedLight = ExtendedColorScheme(
    success = ExtendedColorFamily(
        md_theme_light_success,
        md_theme_light_onSuccess,
        md_theme_light_successContainer,
        md_theme_light_onSuccessContainer,
    ),
)

val extendedDark = ExtendedColorScheme(
    success = ExtendedColorFamily(
        md_theme_dark_success,
        md_theme_dark_onSuccess,
        md_theme_dark_successContainer,
        md_theme_dark_onSuccessContainer,
    ),
)

@Immutable
data class ExtendedColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

@Immutable
data class ContactAvatarColorThemeLight(
    val colors: List<String> = listOf(
        "#DB4437",
        "#E91E63",
        "#9C27B0",
        "#673AB7",
        "#3F51B5",
        "#4285F4",
        "#039BE5",
        "#0097A7",
        "#009688",
        "#0F9D58",
        "#689F38",
        "#EF6C00",
        "#FF5722",
        "#757575",
    )
)

@Immutable
data class ContactAvatarColorSchemeDark(
    val colors: List<String> = listOf(
        "#C53929",
        "#C2185B",
        "#7B1FA2",
        "#512DA8",
        "#303F9F",
        "#3367D6",
        "#0277BD",
        "#006064",
        "#00796B",
        "#0B8043",
        "#33691E",
        "#E65100",
        "#E64A19",
        "#424242",
    )
)
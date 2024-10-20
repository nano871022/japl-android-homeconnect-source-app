package co.com.japl.homeconnect.ui.composite.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.utils.MAIN_MENU
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    val scaleAnimation: Animatable<Float,AnimationVector1D> = remember {Animatable(0f)}

    AnimationSplash(scaleAnimation = scaleAnimation, navController = navController)
    DesignSplash()
}

@Composable
fun DesignSplash() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary)
    ,verticalArrangement = Arrangement.Center
    ,horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.mipmap.ic_launcher_foreground)
            , contentDescription = stringResource(id = R.string.description_logo)
        )
    }
}

@Composable
fun AnimationSplash(scaleAnimation:Animatable<Float,AnimationVector1D>,navController:NavHostController) {
 LaunchedEffect(key1 = true ){
    scaleAnimation.animateTo(
        targetValue = 0.5F
        , animationSpec = tween(
            durationMillis = 1000
            , easing = {
                OvershootInterpolator(2F).getInterpolation(it)
            }
        )
    )

     delay(timeMillis = 2000)

     navController.navigate(MAIN_MENU.MAIN.name){
         popUpTo(MAIN_MENU.MAIN.name){inclusive = true}
     }
 }
}

@Composable
@Preview
fun previewSplash() {
    co.com.japl.ui.theme.MaterialThemeComposeUI {
        DesignSplash()
    }
}
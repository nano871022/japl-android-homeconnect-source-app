package co.com.japl.homeconnect.ui.composite.navigator

import android.content.Context
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.com.alameda181.unidadresidencialalameda181.BuildConfig
import co.com.japl.homeconnect.ui.composite.Main
import co.com.japl.homeconnect.ui.composite.views.HomeScreen
import co.com.japl.homeconnect.ui.composite.splash.SplashScreen
import co.com.japl.homeconnect.ui.composite.views.AdministrationPayment
import co.com.japl.homeconnect.ui.composite.views.DocumentList
import co.com.japl.homeconnect.ui.composite.views.GeneralInformation
import co.com.japl.homeconnect.ui.composite.views.LocalRepairs
import co.com.japl.homeconnect.ui.composite.views.Location
import co.com.japl.homeconnect.ui.composite.views.MovementInformation
import co.com.japl.homeconnect.ui.composite.views.PaymentInformation
import co.com.japl.homeconnect.ui.composite.views.Regulation
import co.com.japl.homeconnect.ui.composite.views.Schedule
import co.com.japl.homeconnect.ui.composite.views.SendEmailPqrs
import co.com.japl.homeconnect.utils.DrawerRoutes
import co.com.japl.homeconnect.utils.MAIN_MENU
import co.com.japl.homeconnect.utils.MenuOptions
import co.com.japl.homeconnect.utils.TopMenuOption

@RequiresApi(34)
@Composable
fun NavigatorSplash(navController: NavHostController,modifier:Modifier,context:Context){
    NavHost(navController = navController, startDestination = MAIN_MENU.SPLASH.name,modifier = modifier) {
        composable(MAIN_MENU.SPLASH.name) {
            SplashScreen(navController = navController)
        }

        composable(MAIN_MENU.MAIN.name) {
            Main(context = context)
        }
    }
}

@RequiresApi(34)
@Composable
fun Navigator(navController: NavHostController,modifier:Modifier,context:Context){
    NavHost(navController = navController, startDestination = DrawerRoutes.HOME.name,modifier = modifier) {

        composable(DrawerRoutes.HOME.name) { HomeScreen() }

        composable(MenuOptions.ABOUT.name){
            co.com.japl.homeconnect.about.UI.About(BuildConfig.VERSION_NAME)
        }

        composable(TopMenuOption.SCHEDULES.name){Schedule()}

        composable(TopMenuOption.ADMINISTRATION_PAYMENT.name){AdministrationPayment()}

        composable(DrawerRoutes.LOCATION.name){ Location() }

        composable(DrawerRoutes.REGULATION.name){ Regulation() }

        composable(DrawerRoutes.GENERAL_INFORMATION.name){ GeneralInformation() }

        composable(DrawerRoutes.PAYMENT_INFORMATION.name){ PaymentInformation() }

        composable(DrawerRoutes.MOVEMENT_INFORMATION.name){ MovementInformation() }

        composable(DrawerRoutes.LOCAL_REPAIRS.name){ LocalRepairs() }

        composable(DrawerRoutes.DOCUMENTS_LIST.name){ DocumentList()}

        composable (DrawerRoutes.PQRs.name){SendEmailPqrs()}

        //composable(DrawerRoutes.RENTAL_INFORMATION.name){ RentalInformation() }
    }
}
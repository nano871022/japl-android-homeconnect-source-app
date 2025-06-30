package co.com.japl.homeconnect.ui.composite.topbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.ui.composite.menuoptions.MenuOptions
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.japl.homeconnect.utils.DrawerRoutes
import co.com.japl.homeconnect.utils.TopMenuOption
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBar(
    pagerState: PagerState,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    drawerState:DrawerState,
    navController: NavHostController,
){
    val context = LocalContext.current.applicationContext
    val rememberExpanderOptions = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val resource = context.resources
    TopAppBar(title = {
              ClickableText(text = buildAnnotatedString {
                    withStyle(style=SpanStyle(color=MaterialTheme.colorScheme.primary)){append(stringResource(id = R.string.name_first))}
                    withStyle(style = SpanStyle(color=MaterialTheme.colorScheme.secondary)){append(stringResource(id = R.string.name_second))}
                } ,modifier = Modifier
                    .padding(5.dp)
                  ,style= MaterialTheme.typography.titleLarge
                ,onClick = {
                    NavTo.nav(navController, DrawerRoutes.HOME.name)
                })
                },
                modifier = modifier.statusBarsPadding(),
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {drawerState.open()}
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_menu_24),
                            contentDescription = stringResource(id = R.string.slide_menu)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(TopMenuOption.ADMINISTRATION_PAYMENT.name){
                        }
                    }) {
                        Icon(painter = painterResource(id = TopMenuOption.ADMINISTRATION_PAYMENT.icon)
                            , contentDescription = stringResource(id = TopMenuOption.ADMINISTRATION_PAYMENT.title))
                    }

                    IconButton(onClick = { NavTo.nav(navController,TopMenuOption.SCHEDULES.name) }) {
                        Icon(painter = painterResource(id = TopMenuOption.SCHEDULES.icon)
                            , contentDescription = stringResource(id = TopMenuOption.SCHEDULES.title))
                    }

                    IconButton(onClick = {
                        rememberExpanderOptions.value = !rememberExpanderOptions.value
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_vert_24),
                            contentDescription = stringResource(id = R.string.menu_more)
                        )
                    }

                    MenuOptions(expandedRemember = rememberExpanderOptions, onClick = {
                        NavTo.nav(navController,it)
                    })
                }
                , colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Gray.copy(alpha = 0.2f))
            )
}

object NavTo{
    fun nav(navController: NavHostController, item:String){
        checkNotNull(navController){"NavController empty"}
        checkNotNull(item){"Item empty"}
        navController.navigate(item.lowercase()){
            popUpTo(item.lowercase())
            launchSingleTop = true
            restoreState = true
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun previewAppBar(){
    val pagerState = rememberPagerState {
        0
    }
    MaterialThemeComposeUI {
        HomeTopAppBar(
            pagerState = pagerState,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            drawerState = DrawerState(DrawerValue.Closed),
            navController = rememberNavController()
        )
    }
}

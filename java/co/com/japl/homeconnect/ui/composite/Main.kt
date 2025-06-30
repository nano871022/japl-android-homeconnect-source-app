package co.com.japl.homeconnect.ui.composite

import android.content.Context
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.japl.homeconnect.ui.composite.bottomBar.BottomBar
import co.com.japl.homeconnect.ui.composite.drawer.drawerContent
import co.com.japl.homeconnect.ui.composite.navigator.Navigator
import co.com.japl.homeconnect.ui.composite.topbar.HomeTopAppBar

@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable fun Main (context: Context){
    val state = rememberDrawerState(DrawerValue.Closed)
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        0
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()


    ModalNavigationDrawer(
        drawerState = state
        , drawerContent = {
            drawerContent(scope = scope, state = state, navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopAppBar(pagerState = pagerState, scrollBehavior = scrollBehavior,drawerState = state, navController = navController)
            }, modifier = Modifier,
            bottomBar = {
                BottomBar(navigation = navController)
            }

        ) {
            Navigator(navController = navController, modifier = Modifier.padding(it), context = context)
        }
    }
}


@RequiresApi(34)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview(){
    MaterialThemeComposeUI {
        Main(context = LocalContext.current)
    }
}


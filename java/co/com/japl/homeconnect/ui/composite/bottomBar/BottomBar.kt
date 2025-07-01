package co.com.japl.homeconnect.ui.composite.bottomBar

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.ui.composite.topbar.NavTo
import co.com.japl.homeconnect.utils.DrawerRoutes

@Composable
fun BottomBar(navigation:NavController){
    val backStack by navigation.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: DrawerRoutes.HOME.name
    val context = LocalContext.current
    val resource = LocalContext.current.resources
    val openDialog = remember { mutableStateOf(false) }
    val  modifier = Modifier.height(50.dp)
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ){

        BottomNavigationItem(
            selected = currentRoute == DrawerRoutes.HOME.name,
            label={ Text(text = "home") },
            alwaysShowLabel = false,
            onClick = {  navigation.navigate(DrawerRoutes.HOME.name) },
            icon = { Icon(painterResource(id = R.drawable.baseline_home_24),contentDescription = "") }
            ,modifier = modifier
        )

        BottomNavigationItem(
            selected = currentRoute == "",
            label={ Text(text = "call") },
            alwaysShowLabel = false,
            onClick = { val phoneNumber = resource.getString(R.string.number_contact).replace(" ","")
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent) },
            icon = { Icon(painterResource(id = R.drawable.baseline_phone_24),contentDescription = "") }
            ,modifier = modifier
        )

        BottomNavigationItem(
            selected = currentRoute == "",
            label={ Text(text = "WhatSapp") },
            alwaysShowLabel = false,
            onClick = {
                val phoneNumber = resource.getString(R.string.whatsapp_number)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$phoneNumber/"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            },
            icon = { Icon(painterResource(id = R.drawable.ic_action_whatsap),contentDescription = "") }
            ,modifier = modifier
        )

        BottomNavigationItem(
            selected = currentRoute == "",
            label={ Text(text = "Email") },
            alwaysShowLabel = false,
            onClick = {
                val email = resource.getString(R.string.email_contact)
                val intent = Intent(Intent.ACTION_SEND, Uri.parse("mailto:$email"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, "Buenos Dias")
                intent.putExtra(android.content.Intent.EXTRA_CC, email)
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Asunto")
                val intentEnd = Intent.createChooser(intent,"Send Email Using:")
                intentEnd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intentEnd)
            },
            icon = { Icon(painterResource(id = R.drawable.baseline_email_24),contentDescription = "") }
            ,modifier = modifier

        )

        BottomNavigationItem(
            selected = currentRoute == DrawerRoutes.Messages.name,
            label={ Text(
                        text = resource.getString(R.string.short_menu_messages),
                        fontSize = TextUnit(12f, TextUnitType.Sp)) },
            alwaysShowLabel = false,
            onClick = {
                openDialog.value = true
                navigation.navigate(DrawerRoutes.Messages.name)
            },
            icon = {
              Icon(painterResource(id = R.drawable.ic_action_message),contentDescription = "")
            }
            ,modifier = modifier

        )
    }
}


@Composable
@Preview(showSystemUi = true)
fun preview(){
    MaterialThemeComposeUI {
        BottomBar(navigation = rememberNavController())
    }
}
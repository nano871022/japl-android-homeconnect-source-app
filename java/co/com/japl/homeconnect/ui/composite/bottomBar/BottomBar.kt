package co.com.japl.homeconnect.ui.composite.bottomBar

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.ui.composite.topbar.NavTo
import co.com.japl.homeconnect.utils.DrawerRoutes

@Composable
fun BottomBar(navigation:NavController){
    val currentRoute = navigation.currentDestination?.route
    val context = LocalContext.current
    val resource = LocalContext.current.resources
    val openDialog = remember { mutableStateOf(false) }
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ){

        BottomNavigationItem(
            selected = currentRoute == "",
            label={ Text(text = "home") },
            alwaysShowLabel = false,
            onClick = {  navigation.navigate(DrawerRoutes.HOME.name) },
            icon = { Icon(painterResource(id = R.drawable.baseline_home_24),contentDescription = "") }

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

        )

        BottomNavigationItem(
            selected = currentRoute == "",
            label={ Text(text = "wWw.") },
            alwaysShowLabel = true,
            onClick = {
                openDialog.value = true
                Handler(Looper.getMainLooper()).postDelayed( {
                    val link = resource.getString(R.string.web_site_link)
                    val uri = Uri.parse(link)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    true
                },3000)
            },
            icon = {
            //    Icon(painterResource(id = R.drawable.outline_web_24),contentDescription = "")
            }

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
package co.com.japl.homeconnect.ui.composite.menuoptions

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.MenuItem
import co.com.japl.homeconnect.utils.MenuOptions
import kotlinx.coroutines.launch

@Composable
fun MenuOptions(expandedRemember:MutableState<Boolean>,onClick: (String) -> Unit) {
    val context = LocalContext.current.applicationContext
    val resource = context.resources
    DropdownMenu(
        expanded = expandedRemember.value
        , onDismissRequest = { expandedRemember.value = !expandedRemember.value}
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
            , verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
           getMenu(resource).forEach {
                MenuItems(it,expandedRemember, onClick = onClick)
            }
        }
    }
}

fun getMenu(resource:Resources):List<MenuItem>{
  return MenuOptions.values().map { opt->
      opt.url?.takeIf { true }?.let{
          MenuItem(name=resource.getString(opt.title),iconDrawer=opt.icon,route="", web = resource.getString(opt.url))
      }?: MenuItem(name=resource.getString(opt.title),iconDrawer=opt.icon,route=opt.name)
  }.toList()
}

@Composable
fun MenuItems(menuItem:MenuItem,expandedRemember:MutableState<Boolean>,onClick : (String) -> Unit){
    val context = LocalContext.current
    DropdownMenuItem(
        text = {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = menuItem.iconDrawer),
                    contentDescription = menuItem.name
                )
                Text(text = menuItem.name)
            }
        }
        , onClick = {
            menuItem.web.takeIf { it.isNotBlank()  }?.let {
                Intent(Intent.ACTION_VIEW, Uri.parse(menuItem.web.trim())).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }?:onClick(menuItem.route)
            expandedRemember.value = !expandedRemember.value
        }
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
fun previewMenuOptions(){
    MenuOptions( expandedRemember = remember { mutableStateOf(true) }, onClick ={})
}
package co.com.japl.homeconnect.ui.composite.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.japl.homeconnect.model.MenuItem

@Composable
fun DrawerItem(menuItem: MenuItem, modifier:Modifier = Modifier, onItemClick:(String)->Unit) {
   Column(
       modifier = Modifier.clickable {onItemClick(menuItem.route)}
   ) {
       Row (verticalAlignment = Alignment.CenterVertically
       , modifier = modifier.padding(8.dp)){
           Icon(painter = painterResource(id = menuItem.iconDrawer), contentDescription = menuItem.name)
           Text(
               text = menuItem.name
               ,modifier = Modifier.padding(horizontal = 10.dp)
           )
       }
       Divider()
   }
}
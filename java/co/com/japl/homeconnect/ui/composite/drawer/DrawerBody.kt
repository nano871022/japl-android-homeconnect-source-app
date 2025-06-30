package co.com.japl.homeconnect.ui.composite.drawer

import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun DrawerBody(
    menuItems: List<MenuItem>
    , scope: CoroutineScope
    , modifier: Modifier = Modifier
    , onItemClick: (String) -> Unit
){
    LazyColumn(modifier = modifier){
        items(menuItems) { menuItem ->
            DrawerItem( menuItem, modifier = Modifier){
                scope.launch {
                }
                onItemClick(menuItem.route)
            }

        }
    }
}
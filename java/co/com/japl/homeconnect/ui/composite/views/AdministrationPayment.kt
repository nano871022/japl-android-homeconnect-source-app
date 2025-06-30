package co.com.japl.homeconnect.ui.composite.views

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.text.HtmlCompat
import co.com.alameda181.unidadresidencialalameda181.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrationPayment(){
    val context = LocalContext.current.applicationContext
    val resource = context.resources
    val openDialog = remember { mutableStateOf(false) }
    val html = remember {HtmlCompat.fromHtml(resource.getString(R.string.pse_info),HtmlCompat.FROM_HTML_MODE_LEGACY)}
    val colorPrimary = MaterialTheme.colorScheme.primary.toArgb()
    Column( verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    , modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {

        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    movementMethod = LinkMovementMethod.getInstance()
                }
                      },
            update = {it.text = html
                     it.setTextColor(colorPrimary)},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        when {
            openDialog.value -> {
                DialogMessage(onDismiss = { openDialog.value = false })
            }
        }

        Card(modifier=Modifier.padding(10.dp)
            ,onClick = {
            openDialog.value = true
            Handler(Looper.getMainLooper()).postDelayed( {
                val link = resource.getString(R.string.payment_link)
                val uri = Uri.parse(link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                true
            },3000)
        }){
            Column(modifier=Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Image(painter = painterResource(id = R.drawable.pse), contentDescription = "PSE")
                Text(text = stringResource(id = R.string.go_pse))
            }
        }
    }
}

@Composable
fun DialogMessage(onDismiss :()->Unit){
    Dialog(onDismissRequest = {onDismiss()}) {
        Card (modifier = Modifier.padding(10.dp)){
            Column( verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.payment), modifier = Modifier
                        .padding(5.dp)
                )

                Text(text = "...")
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun previewAdministrationPayment(){

    co.com.japl.ui.theme.MaterialThemeComposeUI {
        AdministrationPayment()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0XFF111111, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun previewAdministrationPaymentNight(){
    co.com.japl.ui.theme.MaterialThemeComposeUI {
        AdministrationPayment()
    }
}
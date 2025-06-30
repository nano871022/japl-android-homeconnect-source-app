package co.com.japl.homeconnect.ui.composite.views

import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import co.com.alameda181.unidadresidencialalameda181.R

@Composable
fun PaymentInformation() {
    val context = LocalContext.current.applicationContext
    val resource = context.resources
    val html = remember {HtmlCompat.fromHtml(resource.getString(R.string.payment_info),HtmlCompat.FROM_HTML_MODE_COMPACT)}
    val colorPrimary = MaterialTheme.colorScheme.primary.toArgb()
    Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(5.dp)
    ) {

        Image(painter = painterResource(id = R.drawable.pse), contentDescription = "pse")

        AndroidView(factory = {
            TextView(it).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
            update = { it.text = html
            it.setTextColor(colorPrimary)})
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun previewPaymentInformation() {
    co.com.japl.ui.theme.MaterialThemeComposeUI {
        PaymentInformation()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0XFF111111, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun previewPaymentInformationNight() {
    co.com.japl.ui.theme.MaterialThemeComposeUI(darkTheme = true) {
        PaymentInformation()
    }
}
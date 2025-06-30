package co.com.japl.homeconnect.ui.composite.views

import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.alameda181.unidadresidencialalameda181.R

@Composable
fun MovementInformation (){

    Column(
        modifier= Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        ScheduleMovement()

        MoreInformationMovement()

        RequirementsMovement()
    }
}

@Composable
fun RequirementsMovement(){

    val text = stringResource(id = R.string.require_for_movement_detail)
    val html = remember { HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)}
    val colorPrimary = MaterialTheme.colorScheme.primary.toArgb()
    Row(modifier = Modifier.padding(top=10.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_rule_24),
            contentDescription = stringResource(id = R.string.require_for_movement)
        )
        Text(text = stringResource(id = R.string.require_for_movement),modifier= Modifier.padding(start=5.dp))

    }

    Divider()

    AndroidView(
        factory = {
            TextView(it).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = { it.text = html
                 it.setTextColor(colorPrimary)},
        modifier = Modifier.padding(top=5.dp)
    )
}

@Composable
fun MoreInformationMovement(){

    val text = stringResource(id = R.string.schedule_movement_info_detail)
    val html = remember { HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)}
    val colorPrimary = MaterialTheme.colorScheme.primary.toArgb()
    Row(modifier = Modifier.padding(top=10.dp)) {
        Icon(
            painter = painterResource(id = co.com.japl.homeconnect.about.R.drawable.baseline_info_24),
            contentDescription = stringResource(id = R.string.schedule_movement_information)
        )
        Text(text = stringResource(id = R.string.schedule_movement_information),modifier= Modifier.padding(start=5.dp))

    }

    Divider()

    AndroidView(
        factory = {
            TextView(it).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = { it.text = html
                 it.setTextColor(colorPrimary)},
        modifier = Modifier.padding(top=5.dp)
    )

}

@Composable
fun ScheduleMovement(){
    val text = stringResource(id = R.string.schedule_movement_date)
    val html = remember { HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)}
    val colorPrimary = MaterialTheme.colorScheme.primary.toArgb()
    val scheduleOut = stringResource(id = R.string.schedule_out_dont_allow)
    val htmlScheduleOut = remember { HtmlCompat.fromHtml(scheduleOut, HtmlCompat.FROM_HTML_MODE_COMPACT)}
    Row() {
        Icon(
            painter = painterResource(id = co.com.japl.homeconnect.about.R.drawable.baseline_schedule_24),
            contentDescription = stringResource(id = R.string.schedule_movement)
        )
        Text(text = stringResource(id = R.string.schedule_movement),modifier= Modifier.padding(start=5.dp))

    }

    Divider()

    AndroidView(
        factory = {
            TextView(it).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = html
            it.setTextColor(colorPrimary)
                 },
        modifier = Modifier.padding(top=5.dp)
    )

    AndroidView(
        factory = {
            TextView(it).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlScheduleOut
            it.setTextColor(colorPrimary)
                 },
        modifier = Modifier.padding(top=5.dp)
    )

    Text(text = stringResource(id = R.string.holiday_dont_allow),modifier= Modifier.padding(start=5.dp))
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun previewMovementInformation(){
    MaterialThemeComposeUI {
        MovementInformation()
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0XFF111111, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun previewMovementInformationNight(){
    MaterialThemeComposeUI {
        MovementInformation()
    }
}
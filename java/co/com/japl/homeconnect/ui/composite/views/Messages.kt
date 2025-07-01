package co.com.japl.homeconnect.ui.composite.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.Message.MessageViewModel
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.japl.ui.theme.values.Dimensions
import co.japl.android.homeconnect.model.models.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Messages(viewModel:MessageViewModel= hiltViewModel()){
    val load by remember { viewModel.loading }
    val progress by remember  { viewModel.progress }
    LaunchedEffect(key1 = Unit)  {
        viewModel.main()
    }
    if(load){
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
    }else{
        ShowMessages(viewModel.list)
    }
}

@Composable
fun ShowMessages(list:List<Message>){
    Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        Text(text = stringResource(id = R.string.admin_messages), modifier = Modifier.padding(Dimensions.PADDING_SHORT))
        list.takeIf { it.isNotEmpty() }?.sortedByDescending { it.date }?.forEach {
            LoadMessage(message = it)
        }?: Text(text = stringResource(id = R.string.empty_message), modifier = Modifier.padding(Dimensions.PADDING_SHORT))
    }
}

@Composable
fun  LoadMessage(message:Message){
        Card (modifier = Modifier
            .padding(Dimensions.PADDING_SHORT)
            .fillMaxWidth()
            , elevation = Dimensions.PADDING_SHORT
            , backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface ){
            Column (modifier=Modifier.padding(Dimensions.PADDING_SHORT)) {
                Text(text=message.message?:"Issue Loading Message"
                    , textAlign = TextAlign.Justify
                    , color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    , modifier=Modifier)
                Text(text=(message.date?:LocalDateTime.now()).format(DateTimeFormatter.ofPattern("MMMM dd yyyy hh:mm a"))
                    , color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    , modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(top = Dimensions.PADDING_TOP))
            }
        }
}

@Composable
@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun Preview(){
    val viewModel = getViewModel()
    MaterialThemeComposeUI {
        Messages(viewModel)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewDark(){
    val viewModel = getViewModel()
    MaterialThemeComposeUI {
        Messages(viewModel)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewDark2(){
    val viewModel = getViewModel()
    viewModel.list.clear()
    MaterialThemeComposeUI {
        Messages(viewModel)
    }
}

private fun getViewModel():MessageViewModel{
    val viewModel = MessageViewModel(null)
    viewModel.loading.value = false
    viewModel.progress.value = 1.0f
    viewModel.list.add(Message(0,"Message Load 1", LocalDateTime.now().minusDays(1)))
    viewModel.list.add(Message(1,"Message Load 2 asdaqweqwe asdasdqwe adsasdas qweqwe asdasd qweqwe asdasd csd qweqwe asdasd qweqwe a asdas qweqwe asdasdasdasdasdasd", LocalDateTime.now().minusHours(5)))
    viewModel.list.add(Message(2,"Message Load 3", LocalDateTime.now()))
    return viewModel
}
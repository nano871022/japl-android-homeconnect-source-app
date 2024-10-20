package co.com.japl.homeconnect.ui.composite.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.material.icons.rounded.InsertDriveFile
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.Documents.DocumentListModelView
import co.com.japl.homeconnect.core.model.Document

import co.com.japl.ui.theme.values.Dimensions


import co.com.japl.ui.components.Title


import java.time.LocalDateTime

@Composable
fun DocumentList(viewModel: DocumentListModelView = hiltViewModel()) {
    val loader by remember {viewModel.loader}
    val progress by remember {viewModel.progress}

    if(viewModel.isNetworkAvailable(LocalContext.current)) {
        LaunchedEffect(key1 = Unit) {
            viewModel.main()
        }

        if (loader) {
            Column {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(text = stringResource(id = R.string.loading))
            }
        } else {
            Body(viewModel);
        }
    }else{
        Title(title = stringResource(id = R.string.doc_files_title),
            iconPainter = painterResource(id = R.drawable.baseline_folder_24)
        ) {
            Text(
                text = stringResource(id = R.string.no_network_connection),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Body(viewModel: DocumentListModelView){
    val list = remember {viewModel.list}
    val context = LocalContext.current
    val colorBackground = MaterialTheme.colorScheme.onBackground

    Title(title = stringResource(id = R.string.doc_files_title),
        iconPainter = painterResource(id = R.drawable.baseline_folder_24)
      // , colorTitleText = MaterialTheme.colorScheme.primary
    ) {

        Surface (modifier = Modifier.padding(Dimensions.PADDING_SHORT)
            , shape= RoundedCornerShape(10.dp)){

            Column(modifier = Modifier.padding(Dimensions.PADDING_SHORT)) {
                Text(
                    text = stringResource(id = R.string.description_folder_documents),
                    textAlign = TextAlign.Justify
                )
            }
        }


        if (list.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
                items(list.size) { index ->
                    Item(list[index], onClick = { viewModel.descargar(it, context) })
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.there_arent_any_documents_to_show),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Item(doc: Document, onClick:(Document)->Unit){
    val status = remember {mutableStateOf(false)}
    Card (modifier=Modifier.padding(5.dp).height(120.dp)
        ,onClick={
            status.value = true
    }){
        Column (modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()) {
            Icon(
                imageVector = Icons.Rounded.InsertDriveFile,
                contentDescription = doc.name
                , modifier = Modifier
                    .fillMaxWidth()
                    .width(60.dp)
                    .height(60.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                ,tint=MaterialTheme.colorScheme.primary
            )

            Text(text = doc.name
                , color=MaterialTheme.colorScheme.onSurfaceVariant
                , textAlign = TextAlign.Center
                , modifier=Modifier.fillMaxWidth())

        }
    }
    if(status.value){
        InfoFile(
            file = doc,
            showMe = status,
            download = onClick)
    }
}

@Composable
private fun InfoFile(file:Document,showMe:MutableState<Boolean>,download:(Document)->Unit){
  Dialog(onDismissRequest = { showMe.value = false}) {
      Card (modifier=Modifier.padding(Dimensions.PADDING_TOP)) {

        Column (modifier = Modifier.padding(Dimensions.PADDING_TOP)){

            Row {

                Text(text = stringResource(id = R.string.name_file), modifier=Modifier.padding(end=Dimensions.PADDING_SPACE_BETWEEN_FIELD_RIGHT))

                Text(text = file.name)
            }

            Row {
                Icon(imageVector = Icons.Rounded.InsertDriveFile
                    , contentDescription = file.name
                    ,tint=MaterialTheme.colorScheme.onPrimary
                    , modifier = Modifier.size(150.dp)
                        )

                Column {




                Column(modifier=Modifier.padding(bottom = Dimensions.PADDING_BOTTOM)) {
                    Text(text = stringResource(id = R.string.file_type), modifier=Modifier.padding(end=Dimensions.PADDING_SPACE_BETWEEN_FIELD_RIGHT))

                    Text(text = file.mimeType)
                }
                    Row(modifier=Modifier.padding(bottom = Dimensions.PADDING_BOTTOM)) {
                        Text(text = stringResource(id = R.string.version_file), modifier=Modifier.padding(end=Dimensions.PADDING_SPACE_BETWEEN_FIELD_RIGHT))

                        Text(text = file.version)
                    }
                    Column(modifier=Modifier.padding(bottom = Dimensions.PADDING_BOTTOM)) {
                        Text(text = stringResource(id = R.string.create_date), modifier=Modifier.padding(end=Dimensions.PADDING_SPACE_BETWEEN_FIELD_RIGHT))

                        Text(text = file.date.toString())
                    }
                }
            }



            Text(text = stringResource(id = R.string.description_file))

            Row {
                file.description?.let { Text(text = it , modifier=Modifier.weight(2f)) }

                IconButton(onClick = { download(file) }) {
                   Icon(
                        imageVector = Icons.Rounded.Downloading,
                        contentDescription = file.name,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

        }
        }
  }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
internal fun previewDocumentList(){
    MaterialThemeComposeUI {
        DocumentList(getViewModel())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF111111)
internal fun previewDocumentListDark(){
    MaterialThemeComposeUI {
        DocumentList(getViewModel())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
internal fun previewDocumentList1(){
    val viewModel = getViewModel()
    viewModel.list.clear()
    MaterialThemeComposeUI {
        DocumentList(viewModel)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0XFF111111, uiMode = Configuration.UI_MODE_NIGHT_YES)
internal fun previewDocumentList1Dark(){
    val viewModel = getViewModel()
    viewModel.list.clear()
    MaterialThemeComposeUI {
        DocumentList(viewModel)
    }
}

@Composable
private fun getViewModel(): DocumentListModelView {
    val viewModel = DocumentListModelView(null)
    viewModel.loader.value = false
    viewModel.preview = true
    viewModel.list.add(
        Document(
        name = "Document 1",
        url = "https://www.google.com",
        date = LocalDateTime.now(),
        version = "1",
            id = "1",
            mimeType = "application/pdf"
    ))
    viewModel.list.add(
        Document(
            name = "Document 2",
            url = "https://www.google.com",
            date = LocalDateTime.now(),
            version = "1",
            id = "2",
            mimeType = "application/pdf"

        ))
    viewModel.list.add(
        Document(
            name = "Document 3",
            url = "https://www.google.com",
            date = LocalDateTime.now(),
            version = "1",
            id = "3",
            mimeType = "application/pdf"
        ))
    viewModel.list.add(
        Document(
            name = "Document 4",
            url = "https://www.google.com",
            date = LocalDateTime.now(),
            version = "1",
            id = "4",
            mimeType = "application/pdf"
        ))
    viewModel.list.add(
        Document(
            name = "Document 5",
            url = "https://www.google.com",
            date = LocalDateTime.now(),
            version = "1",
            id = "5",
            mimeType = "application/pdf"
        ))
    return viewModel
}



package co.com.japl.homeconnect.ui.composite.views

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.com.japl.ui.theme.MaterialThemeComposeUI
import co.com.japl.ui.theme.common.ImageView
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.HomeScreen.HomeScreenModel
import co.com.japl.homeconnect.model.HomeScreen.HomeScreenState
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.delay

val DELAY_CAROUSEL = 10000L

@RequiresApi(34)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenModel:HomeScreenModel = viewModel()
) {
    val homeScreenState by homeScreenModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = {homeScreenState.carouselList.size})
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val openDialog = remember { homeScreenModel.openState }
    val openDialogWeb = remember { homeScreenModel.openState }
    val openDialogName = remember { homeScreenModel.openStateName }
    val locationUrl = remember { homeScreenModel.openStateUrl }
    val locationDrawable = remember { homeScreenModel.openStateSrc }
    val loader = remember { homeScreenModel.isLoader }

    if(loader.value){
        Column{

            LinearProgressIndicator(modifier=Modifier.fillMaxWidth())
            Text(text = stringResource(id = R.string.loading))
        }
    }else {

        if (isDragged.not()) {
            with(pagerState) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {
                    delay(DELAY_CAROUSEL)
                    val nextPage = (currentPage + 1) % pageCount
                    animateScrollToPage(nextPage)
                    currentPageKey = nextPage
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Carousel(
                model = homeScreenModel,
                pagerState = pagerState,
                homeScreenState = homeScreenState,
                openState = openDialogWeb,
                openStateName = openDialogName,
                openStateUrl = locationUrl,
                openStateSrc = locationDrawable
            )

            AllImage(
                model = homeScreenState,
                openState = openDialog,
                openStateName = openDialogName,
                openStateUrl = locationUrl,
                openStateSrc = locationDrawable
            )

        }
        ImageView(
            name = openDialogName.value,
            imageUrl = locationUrl.value,
            openDialog = openDialogWeb
        )
        ImageView(
            name = openDialogName.value,
            imageSrcInt = locationDrawable.intValue,
            openDialog = openDialog
        )

    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Carousel(model: HomeScreenModel, pagerState :PagerState, homeScreenState: HomeScreenState,openState: MutableState<Boolean>,openStateName: MutableState<String>,openStateUrl: MutableState<String>,openStateSrc: MutableIntState) {
    Box {
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 10.dp),
            pageSpacing = 5.dp,
            state = pagerState
            , modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            val image = homeScreenState.carouselList[it]
            if (image.url.isEmpty()) {
                CarouselItem(image.drawable, image.name, onClick = {
                    openState.value = true
                    openStateName.value = image.name
                    openStateSrc.intValue = image.drawable
                })
            } else {
                CarouselItemWeb(url = image.url, drawableName = image.name, onClick = {
                    openState.value = true
                    openStateName.value = image.name
                    openStateUrl.value = image.url
                })
            }

        }

        DotIndicator(
            pageCount = homeScreenState.carouselList.size,
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
private fun AllImage(model: HomeScreenState, openState: MutableState<Boolean>,openStateName: MutableState<String>,openStateUrl: MutableState<String>,openStateSrc: MutableIntState) {
    var placeholder: MemoryCache.Key? = null
    val context = LocalContext.current.applicationContext
    LazyVerticalGrid(columns = GridCells.Fixed(6)) {
        items(model.carouselList.size) {
            if (model.carouselList[it].url.isBlank()) {
                Image(
                    painter = painterResource(id = model.carouselList[it].drawable)
                    , contentDescription = ""
                    , modifier = Modifier.clickable {
                        openStateName.value = "Image ${model.carouselList[it].name}"
                        openStateSrc.value = model.carouselList[it].drawable
                        openStateUrl.value = ""
                        openState.value = true
                    }
                )
            }else{
                val imageRequest = ImageRequest.Builder(context)
                                                .data(model.carouselList[it].url)
                                                .diskCacheKey(model.carouselList[it].url)
                                                .memoryCacheKey(model.carouselList[it].url)
                                                .crossfade(true)
                                                .memoryCachePolicy(CachePolicy.ENABLED)
                                                .diskCachePolicy(CachePolicy.ENABLED)
                                                .networkCachePolicy(CachePolicy.ENABLED)
                                                .build()
                AsyncImage(
                    model = imageRequest,
                    contentDescription = model.carouselList[it].name,
                    placeholder = painterResource(id = co.com.japl.homeconnect.about.R.drawable.baseline_schedule_24),
                    error = ColorPainter(Color.Red),
                    onSuccess = { placeholder = it.result.memoryCacheKey },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            openStateName.value = "Image ${model.carouselList[it].name}"
                            openStateSrc.intValue = -1
                            openStateUrl.value = model.carouselList[it].url
                            openState.value = true
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotIndicator(pageCount: Int, pagerState: PagerState, modifier: Modifier) {

    Row(modifier=modifier){
        repeat(pageCount){
            val color = if(pagerState.currentPage == it) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary

            Box(
                modifier = modifier
                    .padding(top = 20.dp, start = 2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .padding(8.dp)
            ){

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselItem(drawable: Int,drawableName: String,onClick:()->Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    , onClick = {
        onClick.invoke()
        }) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = drawableName,
            modifier=Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselItemWeb(url:String,drawableName:String,onClick: ()->Unit){
    var placeholder: MemoryCache.Key? = null
    val context = LocalContext.current.applicationContext
    val imageRequest = ImageRequest.Builder(context)
        .data(url)
        .diskCacheKey(url)
        .memoryCacheKey(url)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .build()
    Card(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
        , onClick = {
            onClick.invoke()
        }   ) {
        AsyncImage(
            model = imageRequest,
            error = ColorPainter(Color.Red),
            placeholder = painterResource(id = co.com.japl.homeconnect.about.R.drawable.baseline_schedule_24),
            onSuccess = { placeholder = it.result.memoryCacheKey },
            contentDescription = drawableName
            , modifier = Modifier.fillMaxWidth()
        )
    }
}

@RequiresApi(34)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true, showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_NO)
fun preview(){
    val context:Context = LocalContext.current
    MaterialThemeComposeUI {
            HomeScreen()
        }
}





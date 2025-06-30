package co.com.japl.homeconnect.ui.composite.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.ui.theme.MaterialThemeComposeUI
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Location (){
    val context = LocalContext.current.applicationContext
    val location = LatLng(4.758172,-74.040524)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(location,10f)
    }
    Column (
        modifier = Modifier.fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally
    ){


        Image(painter = painterResource(id = R.drawable.alameda181negro), contentDescription = "Pic")

        Text(text = stringResource(id = R.string.app_name)
            , fontSize = 28.sp
            , fontWeight = FontWeight.Bold)

        Text(text = stringResource(id = R.string.state))

        Text(text = stringResource(id = R.string.address))

        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${location.latitude},${location.longitude}"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }) {
            Image(painter = painterResource(id = R.drawable.baseline_place_24)
                , contentDescription = "Location"
                , colorFilter = ColorFilter.tint(Color.Red) )
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ){
            Marker(
                state= MarkerState(position = location),
                title = stringResource(id = R.string.app_name)
                , snippet = stringResource(id = R.string.kind_unit)
            )
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun previewLocation(){
    MaterialThemeComposeUI {
        Location()
    }
}
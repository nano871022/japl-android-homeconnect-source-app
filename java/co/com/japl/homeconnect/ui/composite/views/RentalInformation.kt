package co.com.japl.homeconnect.ui.composite.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RentalInformation() {
    Text(text = "Rental Information")
}

@Composable
@Preview
fun previewRentalInformation() {
    co.com.japl.ui.theme.MaterialThemeComposeUI {
        RentalInformation()
    }
}
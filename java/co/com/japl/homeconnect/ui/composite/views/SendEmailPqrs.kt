package co.com.japl.homeconnect.ui.composite.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.model.SendEmailPqrs.SendEmailPqrs
import co.com.japl.homeconnect.utils.DrawerRoutes

@Composable
fun SendEmailPqrs(vieeModel: SendEmailPqrs = viewModel()) {
    val send = remember {vieeModel.emailSend}
    val email = stringResource(id = R.string.admin_email)
    val template = stringResource(id = R.string.msg_pqr_template)
    val subject = stringResource(id = R.string.pqr_subject_template)

    if(!send.value){
        vieeModel.sendEmail(LocalContext.current, email, subject, template)
    }else{


    }

}
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karen_and.screens.login.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit,
    onNavigateSignUp: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.submit(onNavigateHome)
        }) {
            Text("Ingresar")
        }
    }
}
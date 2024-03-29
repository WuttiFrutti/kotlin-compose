import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import models.Brand
import org.kodein.di.compose.localDI
import org.kodein.di.compose.withDI
import org.kodein.di.jxinject.jx
import models.Car
import org.kodein.di.*
import org.kodein.di.compose.factory
import org.kodein.di.jxinject.jxInjectorModule
import org.kodein.di.jxinject.jxQualifier

@Composable
@Preview
fun app() = withDI({
    import(jxInjectorModule)
    bind<Brand>() with factory { Brand() }
    bindProvider<MakeMaterialTheme> {  MakeMaterialTheme() };
}) {
    val makeMaterialTheme: MakeMaterialTheme by localDI().instance();

    return@withDI makeMaterialTheme();
}


class MakeMaterialTheme() {
    @Composable
    operator fun invoke() {
        val car =  localDI().jx.newInstance<Car>();
        var text by remember { mutableStateOf(car.brand.name) }

        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar { /* Top app bar content */ }
                },
                drawerContent = {
                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                    Divider()
                    // Drawer items
                }
            ) {
                Button(
                    onClick = {
                        text = "Hello, Desktop!"
                    }) {
                    Text(text)
                }
            }



        }
    };
}



fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            app();
        };
    }
}

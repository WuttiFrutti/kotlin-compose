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
import eventhandler.EventHandlerComponent
import eventhandler.interfaces.IGameEvents
import messenger.MessengerComponent
import messenger.interfaces.IMessenger
import org.kodein.di.compose.localDI
import org.kodein.di.compose.withDI
import org.kodein.di.jxinject.jx
import network.NetworkComponent
import network.interfaces.INetwork
import org.kodein.di.*
import org.kodein.di.jxinject.jxInjectorModule
import persistence.PersistenceComponent
import persistence.interfaces.IPersistence

@Composable
@Preview
fun app() = withDI({
    import(jxInjectorModule)

    bind<INetwork>() with factory { jx.newInstance<NetworkComponent>() }
    bind<IPersistence>() with factory { jx.newInstance<PersistenceComponent>() }
    bind<IMessenger>() with factory { jx.newInstance<MessengerComponent>() }
    bind<IGameEvents>() with factory { jx.newInstance<EventHandlerComponent>() }

    bindProvider<MakeMaterialTheme> {  MakeMaterialTheme() };
}) {
    val makeMaterialTheme: MakeMaterialTheme by localDI().instance();
    val eventHandler: IGameEvents by localDI().instance();
    println(eventHandler.gameEventStream)

    return@withDI makeMaterialTheme();
}


class MakeMaterialTheme() {
    @Composable
    operator fun invoke() {
        var text by remember { mutableStateOf("yeet") }

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

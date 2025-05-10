package nl.jaysh.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.jaysh.journal.core.designsystem.JournalTheme
import nl.jaysh.journal.navigation.RootNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JournalTheme {
                val navController = rememberNavController()
                RootNavHost(navController = navController)
            }
        }
    }
}

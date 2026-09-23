package com.udistrital.gestorcasos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.udistrital.gestorcasos.ui.navigation.GestorCasosNavGraph
import com.udistrital.gestorcasos.ui.theme.GestorCasosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorCasosTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GestorCasosNavGraph()
                }
            }
        }
    }
}

package co.edu.udistrital.parcial01_pc.ui.theme.Screens.CaseList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.udistrital.parcial01_pc.ui.theme.Components.CaseItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseListScreen(
    viewModel: CaseListViewModel,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {

    val cases by viewModel.cases.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Casos de Investigación") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo Caso")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar caso por título...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(cases) { caso ->

                    CaseItem(
                        case = caso,
                        onClick = { onNavigateToDetail(caso.id) }
                    )
                }
            }
        }
    }
}
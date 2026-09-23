package com.udistrital.gestorcasos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.gestorcasos.ui.screens.caseform.CaseFormScreen
import com.udistrital.gestorcasos.ui.screens.caselist.CaseListScreen
import com.udistrital.gestorcasos.ui.screens.casedetail.CaseDetailScreen
import com.udistrital.gestorcasos.ui.screens.home.HomeScreen

/**
 * Grafo de navegación central de la app: Home, Listado, Formulario (crear/editar)
 * y Detalle quedan todos conectados y funcionales.
 */
@Composable
fun GestorCasosNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onVerListado = { navController.navigate(Screen.CaseList.route) },
                onCrearCaso = { navController.navigate(Screen.CaseForm.createRoute()) },
                onAbrirCaso = { caseId -> navController.navigate(Screen.CaseDetail.createRoute(caseId)) }
            )
        }

        composable(Screen.CaseList.route) {
            CaseListScreen(
                onBack = { navController.popBackStack() },
                onAbrirCaso = { caseId -> navController.navigate(Screen.CaseDetail.createRoute(caseId)) }
            )
        }

        composable(
            route = Screen.CaseDetail.route,
            arguments = listOf(navArgument("caseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getLong("caseId") ?: 0L
            CaseDetailScreen(
                caseId = caseId,
                onBack = { navController.popBackStack() },
                onEditar = { id -> navController.navigate(Screen.CaseForm.createRoute(id)) }
            )
        }

        composable(
            route = Screen.CaseForm.route,
            arguments = listOf(navArgument("caseId") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getLong("caseId")?.takeIf { it > 0 }
            CaseFormScreen(
                caseId = caseId,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }
    }
}

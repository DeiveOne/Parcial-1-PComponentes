package com.udistrital.gestorcasos.ui.navigation

/**
 * Rutas de navegación de la app. Home queda funcional desde ya; las pantallas de
 * Listado, Formulario (crear/editar) y Detalle se implementarán en la siguiente
 * entrega, pero sus rutas ya quedan reservadas para no reestructurar el grafo después.
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object CaseList : Screen("case_list")

    data object CaseDetail : Screen("case_detail/{caseId}") {
        fun createRoute(caseId: Long) = "case_detail/$caseId"
    }

    data object CaseForm : Screen("case_form?caseId={caseId}") {
        /** @param caseId null = crear caso nuevo; distinto de null = editar caso existente. */
        fun createRoute(caseId: Long? = null) = "case_form?caseId=${caseId ?: -1L}"
    }
}

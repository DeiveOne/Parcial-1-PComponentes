package com.udistrital.gestorcasos.data.model

/**
 * Possible statuses of a case during its lifecycle.
 * Used for list filtering and for the general summary on the Home screen.
 */
enum class CaseStatus(val label: String) {
    ABIERTO("Open"),
    EN_INVESTIGACION("Under investigation"),
    CERRADO("Closed")
}

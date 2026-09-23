package com.udistrital.gestorcasos.data.model

/**
 * Estados posibles de un caso durante su ciclo de vida.
 * Se usa para el filtrado del listado y para el resumen general de la pantalla Home.
 */
enum class CaseStatus(val label: String) {
    ABIERTO("Abierto"),
    EN_INVESTIGACION("En investigación"),
    CERRADO("Cerrado")
}

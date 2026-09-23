# Gestor de Entrevistas (casos criminales)

App Android en **Kotlin + Jetpack Compose + Room**, desarrollada para el ejercicio
de la asignatura, siguiendo la separación **interfaz / lógica / persistencia**.
No usa Java (100% Kotlin) ni menús de Android (nada de `DropdownMenu`,
`NavigationDrawer` ni opciones de menú): el estado de un caso se elige con
`FilterChip`, y las confirmaciones/formularios cortos usan `AlertDialog`.

## Estado actual

Las 4 pantallas están implementadas y conectadas:

- **Home**: resumen general (totales por estado) + casos recientes.
- **Listado**: buscador por título + estado visible en cada caso.
- **Formulario**: crea y edita casos (título, descripción, fecha, estado); si el
  estado es "Cerrado" pide la conclusión.
- **Detalle**: datos del caso, conclusión (si está cerrado), lista de entrevistas
  con sus hallazgos, botón para agregar entrevista, botón para cerrar el caso
  (pide conclusión), botones de editar y eliminar (con confirmación).

## Estructura de paquetes

```
app/src/main/java/com/udistrital/gestorcasos/
├── GestorCasosApplication.kt   -> crea la base de datos y el repositorio (una sola vez)
├── MainActivity.kt             -> punto de entrada, arma tema + navegación
│
├── data/
│   ├── model/                  -> entidades Room: Case, Interview, CaseStatus
│   ├── local/                  -> AppDatabase, CaseDao, InterviewDao, Converters
│   └── repository/             -> CaseRepository (única puerta de entrada a los datos)
│
└── ui/
    ├── theme/                  -> Color.kt, Type.kt, Theme.kt (tema Material 3)
    ├── navigation/             -> Screen.kt (rutas) y NavGraph.kt
    ├── AppViewModelProvider.kt -> fábrica de ViewModels (sin librerías de DI externas)
    ├── components/             -> reutilizables entre pantallas:
    │   ├── StatusChip.kt          chip de color según CaseStatus
    │   ├── EmptyState.kt          mensaje para listas vacías
    │   ├── ConfirmDialog.kt       diálogo genérico sí/no
    │   ├── SearchField.kt         campo de búsqueda
    │   └── DateField.kt           selector de fecha nativo (DatePickerDialog)
    │
    └── screens/
        ├── home/               -> HomeScreen.kt + HomeViewModel.kt
        ├── caselist/           -> CaseListScreen.kt + CaseListViewModel.kt
        ├── caseform/           -> CaseFormScreen.kt + CaseFormViewModel.kt
        └── casedetail/         -> CaseDetailScreen.kt + CaseDetailViewModel.kt
                                    + CaseDetailDialogs.kt (cerrar caso / nueva entrevista)
```

### Por qué esta organización

- **model**: qué se guarda (las tablas).
- **local**: cómo se guarda (Room: DAOs + base de datos).
- **repository**: única clase que conoce a la vez los DAOs; la UI y los ViewModel
  nunca hablan directo con Room.
- **ui/screens/<pantalla>**: cada pantalla tiene su propio ViewModel (estado y
  lógica) y su propio archivo de Composables (solo dibuja lo que el ViewModel entrega).
- **ui/components**: piezas de UI repetidas en más de una pantalla, para no
  duplicar código.

## Modelo de datos

- **Case**: `id, title, description, date, status (CaseStatus), conclusion`
- **Interview**: `id, caseId (FK -> Case), intervieweeName, date, findings`
- **CaseStatus**: `ABIERTO, EN_INVESTIGACION, CERRADO`

Al eliminar un caso se eliminan en cascada sus entrevistas (`onDelete = CASCADE`).

## Verificación de compilación

Este entorno no tiene Android SDK ni acceso al Maven de Google, así que no se
pudo correr `./gradlew build` directamente. En su lugar, se armó un set de
*stubs* (firmas exactas) de Compose/Room/Navigation/Lifecycle/Coroutines y se
compiló el código real de los 30 archivos `.kt` del proyecto con `kotlinc`
1.9.24: **0 errores**. Esto no reemplaza una compilación real con Gradle, pero
da bastante confianza de que la sintaxis y el uso de las APIs es correcto.
Aun así, al abrir el proyecto en Android Studio por primera vez puede pedir
sincronizar/descargar dependencias — dale a "Sync Now" y espera a que termine.

## Cómo abrir el proyecto

1. Abrir Android Studio (versión reciente, Koala o superior).
2. `File > Open` y seleccionar la carpeta `GestorCasos`.
3. Dejar que Gradle sincronice (necesita conexión a internet la primera vez).
4. Ejecutar en un emulador o dispositivo con **Android 7.0 (API 24)** o superior.

## Versiones usadas

- Kotlin 1.9.24 · AGP 8.5.0 · Gradle 8.7
- Compose BOM 2024.06.00 · Material 3
- Room 2.6.1 (con KSP en vez de kapt)
- Navigation Compose 2.7.7

## Pendiente / ideas para mejorar

- Pruebas unitarias (ViewModel y Repository) — la arquitectura ya lo facilita,
  porque `CaseDao`/`InterviewDao` son interfaces fáciles de "fakear".
- Validaciones adicionales en el formulario (por ejemplo, que la fecha no sea futura).
- Mostrar un mensaje de error si falla el guardado.

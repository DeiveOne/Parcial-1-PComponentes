package co.edu.udistrital.parcial01_pc.ui.theme.Screens.AddedItCase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEditCaseViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onTitleChanged(newTitle: String) {
        _title.value = newTitle
        _errorMessage.value = null
    }

    fun onDescriptionChanged(newDescription: String) {
        _description.value = newDescription
        _errorMessage.value = null
    }

    fun saveCase(onSuccess: () -> Unit) {
        val currentTitle = _title.value
        val currentDescription = _description.value

        if (currentTitle.isBlank() || currentDescription.isBlank()) {
            _errorMessage.value = "El título y la descripción son obligatorios."
            return
        }

        println("Simulando guardado: Título: $currentTitle, Desc: $currentDescription")

        onSuccess()
    }
}
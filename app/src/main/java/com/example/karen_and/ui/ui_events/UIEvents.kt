package com.example.karen_and.ui.ui_events

sealed class UIEvents {
    data class ShowSnackbar(val message: String) : UIEvents()
    data class ShowToast(val message: String) : UIEvents()
    data class ShowDialog(val title: String, val message: String) : UIEvents()

    object NavigateBack : UIEvents()
    data class Navigate(val route: String) : UIEvents()

}

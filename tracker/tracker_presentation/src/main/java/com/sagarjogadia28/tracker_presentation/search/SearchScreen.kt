package com.sagarjogadia28.tracker_presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.tracker_presentation.search.components.SearchTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    snackBarHostState: SnackbarHostState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    SearchContent(
        mealName = mealName,
        query = state.query,
        onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it)) },
        onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
        onFocusChange = { viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused)) }
    )
}

@Composable
fun SearchContent(
    mealName: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFocusChange: (FocusState) -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(R.string.add_meal, mealName),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = query,
            onValueChange = onValueChange,
            onSearch = onSearch,
            onFocusChanged = onFocusChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    CalorieTrackerTheme {
        SearchContent(
            mealName = "Breakfast",
            query = "",
            onValueChange = {},
            onSearch = {},
            onFocusChange = {}
        )
    }
}
package com.sagarjogadia28.onboarding_presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme

@Composable
fun ActionButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(text),
            style = textStyle,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonPreview() {
    CalorieTrackerTheme {
        ActionButton(
            text = R.string.next,
            onClick = {}
        )
    }
}
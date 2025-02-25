package com.sagarjogadia28.onboarding_presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme

@Composable
fun SelectableButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(
                color = if (isSelected) color else Color.Transparent
            )
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable { onClick() }
            .padding(LocalSpacing.current.spaceMedium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(text),
            style = textStyle,
            color = if (isSelected) selectedTextColor else color
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SelectableButtonPreview() {
    CalorieTrackerTheme {
        SelectableButton(
            text = R.string.male,
            onClick = {},
            isSelected = true,
            color = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        )
    }
}
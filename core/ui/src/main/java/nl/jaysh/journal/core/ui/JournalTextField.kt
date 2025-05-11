package nl.jaysh.journal.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun JournalTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
) = OutlinedTextField(
    modifier = modifier.fillMaxWidth(),
    enabled = enabled,
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) },
    placeholder = placeholder,
    trailingIcon = trailingIcon,
    visualTransformation = visualTransformation,
    leadingIcon = leadingIcon,
    prefix = prefix,
    suffix = suffix,
    isError = isError,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
)

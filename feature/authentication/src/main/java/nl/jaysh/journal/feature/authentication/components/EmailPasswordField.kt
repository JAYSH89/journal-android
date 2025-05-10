package nl.jaysh.journal.feature.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import nl.jaysh.journal.core.ui.JournalTextField
import nl.jaysh.journal.core.ui.R

@Composable
internal fun EmailPasswordField(
    title: String,
    email: String,
    password: String,
    loading: Boolean,
    modifier: Modifier = Modifier,
    isErrorEmail: Boolean = false,
    isErrorPassword: Boolean = false,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
        )

        JournalTextField(
            value = email,
            label = "Email address",
            enabled = !loading,
            icon = R.drawable.ic_email,
            isError = isErrorEmail,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            onValueChange = onEmailChange,
        )

        if (isErrorEmail) {
            Text(
                text = "Invalid e-mail",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        JournalTextField(
            value = password,
            label = "Password",
            enabled = !loading,
            icon = R.drawable.ic_password,
            isError = isErrorPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = { focusManager.clearFocus() },
            ),
            onValueChange = onPasswordChange,
        )

        if (isErrorPassword) {
            Text(
                text = "Invalid password",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
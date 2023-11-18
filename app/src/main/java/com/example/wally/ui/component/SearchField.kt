package com.example.wally.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wally.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.border
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.example.wally.ui.theme.VioletsBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(searchQuery: String, onValueChange: (String) -> Unit, hideKeyboard : Boolean, onFocusClear: () -> Unit) {

    var isFocused by remember { mutableStateOf(false) }
    val borderColor = if (isFocused) VioletsBlue else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
    var query by rememberSaveable { mutableStateOf(searchQuery) }
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .border(2.5.dp, borderColor, RoundedCornerShape(15.dp)),
        placeholder = { Text(text = "E.g. Palestine") },
        value = query,

        onValueChange = {
            query = it
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }

        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = VioletsBlue,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 10.dp
                ), painter = painterResource(id = R.drawable.search_linear), contentDescription = ""
            )
        },
        shape = RoundedCornerShape(15.dp)
    )

    if (hideKeyboard) {
        isFocused = false
        focusManager.clearFocus()
        // Call onFocusClear to reset hideKeyboard state to false
        onFocusClear()
    }

}


package com.vi5hnu.tipcalculator.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vi5hnu.tipcalculator.R

@Composable
fun InputField(
    modifier:Modifier=Modifier,
    valuState:MutableState<String>,
    labelText:String,
    enabled:Boolean,
    isSingleLine:Boolean,
    imeAction:ImeAction=ImeAction.Next,
    onAction:KeyboardActions=KeyboardActions.Default,
    keyboardType: KeyboardType= KeyboardType.Number) {
        OutlinedTextField(
            modifier = modifier.padding(10.dp),
            value = valuState.value,
            onValueChange ={valuState.value=it},
            label = { Text(text = labelText)},
            leadingIcon = { Icon(imageVector = ImageVector.vectorResource(id = R.drawable.indian_rupee),
                contentDescription = "Rupee Icon" )},
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp),
            enabled=enabled,
            keyboardOptions = KeyboardOptions(keyboardType=keyboardType,
                imeAction = imeAction),
            keyboardActions = onAction
        )
}
package ua.wied.presentation.common.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextOverflow
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@Composable
fun BaseTextField(
    text: String,
    title: String,
    fieldColor: Color? = null,
    trailingIcon: Int? = null,
    textButton: String? = null,
    description: String? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextButtonClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    val backgroundColor = fieldColor ?: colors.secondaryBackground

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
    ) {
        Text(
            text = title,
            color = colors.secondaryText,
            style = typography.body1
        )
        BasicTextField(
            modifier = Modifier.height(52.dp),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = typography.body1.copy(
                color = if(errorMessage != null) colors.errorColor else colors.primaryText
            ),
            maxLines = 1,
            cursorBrush = SolidColor(colors.primaryText),
            visualTransformation = visualTransformation
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(
                        color = backgroundColor,
                        shape = dimen.shape
                    )
                    .border(
                        width = 1.dp,
                        color = if(errorMessage != null) colors.errorColor else Color.Transparent,
                        shape = dimen.shape
                    )
                    .padding(vertical = dimen.paddingM, horizontal = dimen.padding3Xl),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box (
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {

                    description?.let {
                        if (text.isEmpty()) {
                            Text(
                                text = it,
                                color = colors.secondaryText,
                                style = typography.body1,
                                modifier = Modifier.animateContentSize()
                            )
                        }
                    }

                    it.invoke()
                }

                trailingIcon?.let {
                    IconButton(
                        modifier = Modifier.size(dimen.sizeM),
                        onClick = { onTrailingIconClick?.invoke() }
                    ) {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            tint = colors.primaryText
                        )
                    }
                }

            }
        }

        if (errorMessage != null || textButton != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = colors.errorColor,
                        style = typography.body2,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else Spacer(modifier = Modifier)
                textButton?.let { buttonText ->
                    Text(
                        text = buttonText,
                        color = colors.secondaryText,
                        style = typography.body2,
                        modifier = Modifier.clickable { onTextButtonClick?.invoke() }
                    )
                }
            }
        }



    }
}

@Composable
fun PasswordTextField(
    text: String,
    title: String,
    fieldColor: Color? = null,
    textButton: String? = null,
    description: String? = null,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
    onTextButtonClick: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None

    BaseTextField(
        text = text,
        title = title,
        fieldColor = fieldColor,
        description = description,
        errorMessage = errorMessage,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        visualTransformation = visualTransformation,
        textButton = textButton,
        onTextButtonClick = onTextButtonClick,
        trailingIcon = if (passwordVisible) R.drawable.icon_visibility_off else R.drawable.icon_visibility,
        onTrailingIconClick = {
            passwordVisible = !passwordVisible
        }
    )
}


@Composable
fun PhoneTextField(
    text: String,
    title: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit
) {
    BaseTextField(
        text = text,
        title = title,
        errorMessage = errorMessage,
        onValueChange = { newValue ->
            onValueChange(newValue.filterNot { it == ' ' })
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        visualTransformation = phoneNumberVisualTransformation()
    )
}

@Composable
fun UnderlineTextField(
    text: String,
    title: String?,
    maxTextLength: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit
) {
    val textLength = text.length

    BasicTextField(
        modifier = Modifier.height(60.dp),
        value = text,
        onValueChange = {
            if (textLength < maxTextLength) {
                onValueChange(it)
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = typography.body1,
        maxLines = 1,
        singleLine = true,
        cursorBrush = SolidColor(colors.primaryText)
    ) {
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(vertical = dimen.paddingM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box (
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {

                    title?.let {
                        if (text.isEmpty()) {
                            Text(
                                text = it,
                                color = colors.secondaryText,
                                style = typography.body2,
                                modifier = Modifier.animateContentSize()
                            )
                        }
                    }

                    it.invoke()
                }

            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = colors.secondaryText
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(vertical = dimen.padding2Xs),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "$textLength/$maxTextLength",
                    color = colors.secondaryText,
                    style = typography.body4,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    onSearchValueChange: (String) -> Unit = {},
) {

    BasicTextField(
        modifier = modifier.height(52.dp),
        value = text,
        onValueChange = {
            onSearchValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        textStyle = typography.body1,
        maxLines = 1,
        singleLine = true,
        cursorBrush = SolidColor(colors.primaryText)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .background(
                    color = Color(0xFFF4F4FA),
                    shape = dimen.shape
                )
                .padding(vertical = dimen.paddingM, horizontal = dimen.padding3Xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(17.dp),
                painter = painterResource(R.drawable.icon_search),
                tint = colors.primaryText,
                contentDescription = stringResource(R.string.icon)
            )

            Box (
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {

                if (text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search),
                        color = colors.secondaryText,
                        style = typography.body1,
                        modifier = Modifier.animateContentSize()
                    )
                }

                it.invoke()
            }


            IconButton(
                icon = painterResource(R.drawable.icon_microphone),
                backgroundColor = Color.Transparent,
                borderColor = Color.Transparent,
                iconColor = colors.primaryText,
                onClick = {}
            )

        }
    }

}

private fun phoneNumberVisualTransformation(): VisualTransformation {
    return VisualTransformation { text ->
        if (text.text.isEmpty()) {
            TransformedText(text, OffsetMapping.Identity)
        } else {
            val prefix = "+ "
            val transformedText = AnnotatedString(prefix + text.text)

            val offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int =
                    offset + prefix.length

                override fun transformedToOriginal(offset: Int): Int =
                    if (offset >= prefix.length) offset - prefix.length else 0
            }
            TransformedText(transformedText, offsetMapping)
        }
    }
}

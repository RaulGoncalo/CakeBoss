package com.rgosdeveloper.cakeboss.presentation.components.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rgosdeveloper.cakeboss.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(modifier: Modifier = Modifier){
    TopAppBar(
        modifier = modifier,
        title = { Text(text = Constants.TITLE_TOP_BAR) },
        /*actions = {
            Row {

            }
        }*/
    )
}
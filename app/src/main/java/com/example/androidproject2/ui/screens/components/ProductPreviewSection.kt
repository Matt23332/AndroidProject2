package com.example.androidproject2.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidproject2.R
import com.example.androidproject2.data.ProductHighlights
import com.example.androidproject2.data.ProductPreviewState
import com.example.androidproject2.ui.theme.AppTheme


@Composable
fun ProductPreviewSection (
    modifier: Modifier = Modifier,
    state: ProductPreviewState
){
    Box(
        modifier=modifier.height(IntrinsicSize.Max)
    ){
        ProductBackground(
            modifier= Modifier.padding(bottom = 24.dp)
        )
        Content(
            state = state,
            modifier= Modifier
                .statusBarsPadding()
                .padding(top = 24.dp)
        )
    }
}

@Composable
private fun  ProductBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier= modifier
            .fillMaxSize()
            .background(
                color = AppTheme.colors.secondarySurface,
                shape = RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
    )

}

@Composable
private fun Content(
    modifier:Modifier= Modifier,
    state: ProductPreviewState
){
    ConstraintLayout (
        modifier = modifier.fillMaxWidth()
    ){
        val (actionBar, highlights, productImg)=createRefs()

        ActionBar(
            headline = "GreenShop",
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .constrainAs(actionBar) {
                    top.linkTo(parent.top)

                }
        )
        Image(painter = painterResource(id = R.drawable.bananas),
            contentDescription = null,
            contentScale=ContentScale.FillHeight,
            modifier = Modifier
                .height(256.dp)
                .constrainAs(productImg) {
                    end.linkTo(parent.end)
                    top.linkTo(anchor = actionBar.bottom, margin = 28.dp)
                }
        )
        ProductHighlights(
            highlights = state.highlights,
            modifier = Modifier.constrainAs(highlights){
                start.linkTo(anchor=parent.start, margin=19.dp)
                top.linkTo(productImg.top)
            })

    }
}

@Composable
private fun ActionBar(
    modifier: Modifier= Modifier,
    headline: String
){
    Row(
        modifier= modifier.fillMaxWidth(),
        verticalAlignment=Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text=headline,
            style= AppTheme.typography.headline,
            color= AppTheme.colors.onSecondarySurface
        )
        CloseButton( modifier= Modifier)
    }
}

@Composable
private fun CloseButton(
    modifier:Modifier
){
    Surface (
        modifier= modifier,
        shape= RoundedCornerShape(16.dp),
        color= AppTheme.colors.actionSurface,
        contentColor = AppTheme.colors.secondarySurface

    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Icon(painter = painterResource(id = R.drawable.wheat), contentDescription = null, modifier= Modifier.size(24.dp) )
        }

    }
}
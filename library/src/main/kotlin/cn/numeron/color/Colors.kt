package cn.numeron.color

import android.content.res.ColorStateList

fun colorSelector(builder: ColorSelectorBuilder.() -> Unit): ColorStateList {
    val colorSelectorBuilder = ColorSelectorBuilder()
    colorSelectorBuilder.builder()
    return colorSelectorBuilder.build()
}
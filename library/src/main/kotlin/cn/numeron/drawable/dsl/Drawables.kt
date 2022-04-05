package cn.numeron.drawable.dsl

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable

fun shape(shape: Shape = Shape.RECTANGLE, builder: ShapeBuilder.() -> Unit): GradientDrawable {
    val shapeBuilder = ShapeBuilder(shape)
    shapeBuilder.builder()
    return shapeBuilder.build()
}

fun stateList(builder: StateListBuilder.() -> Unit): StateListDrawable {
    val stateListBuilder = StateListBuilder()
    stateListBuilder.builder()
    return stateListBuilder.build()
}

fun ripple(builder: RippleBuilder.() -> Unit): RippleDrawable {
    val rippleBuilder = RippleBuilder()
    rippleBuilder.builder()
    return rippleBuilder.build()
}
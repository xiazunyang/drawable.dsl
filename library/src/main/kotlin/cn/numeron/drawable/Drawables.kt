package cn.numeron.drawable

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable

fun shape(shape: Shape = Shape.RECTANGLE, builder: ShapeBuilder.() -> Unit): GradientDrawable {
    val shapeBuilder = ShapeBuilder(shape)
    shapeBuilder.builder()
    return shapeBuilder.build()
}

fun selector(builder: SelectorBuilder.() -> Unit): StateListDrawable {
    val stateListBuilder = SelectorBuilder()
    stateListBuilder.builder()
    return stateListBuilder.build()
}

fun ripple(builder: RippleBuilder.() -> Unit): RippleDrawable {
    val rippleBuilder = RippleBuilder()
    rippleBuilder.builder()
    return rippleBuilder.build()
}
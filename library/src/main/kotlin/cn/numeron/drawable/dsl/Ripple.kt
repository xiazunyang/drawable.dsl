package cn.numeron.drawable.dsl

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable

class RippleBuilder {

    var color: ColorStateList? = null

    var content: Drawable? = null
    var contentBuilder: (() -> Drawable)? = null

    var mask: Drawable? = null
    var maskBuilder: (() -> Drawable)? = null

    fun color(color: Int): RippleBuilder {
        this.color = ColorStateList.valueOf(color)
        return this
    }

    fun content(builder: () -> Drawable): RippleBuilder {
        contentBuilder = builder
        return this
    }

    fun mask(builder: () -> Drawable): RippleBuilder {
        maskBuilder = builder
        return this
    }

    fun build(): RippleDrawable = RippleDrawable(
        color ?: throw NullPointerException(),
        content ?: contentBuilder?.invoke(),
        mask ?: maskBuilder?.invoke()
    )

}
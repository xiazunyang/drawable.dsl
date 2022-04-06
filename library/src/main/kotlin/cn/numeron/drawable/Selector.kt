package cn.numeron.drawable

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

class SelectorBuilder {

    private var default: Drawable? = null
    private var defaultBuilder: (() -> Drawable)? = null
    private var builders = mutableListOf<StateBuilder>()

    fun defState(drawable: Drawable): SelectorBuilder {
        this.default = drawable
        return this
    }

    fun defState(builder: () -> Drawable): SelectorBuilder {
        defaultBuilder = builder
        return this
    }

    fun addState(state: StateElement, drawable: Drawable): SelectorBuilder {
        val stateBuilder = StateBuilder(state)
        stateBuilder.drawable(drawable)
        builders.add(stateBuilder)
        return this
    }

    fun addState(state: StateElement, builder: () -> Drawable): SelectorBuilder {
        val stateBuilder = StateBuilder(state)
        stateBuilder.drawable(builder)
        builders.add(stateBuilder)
        return this
    }

    fun build(): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        for (builder in builders) {
            stateListDrawable.addState(
                builder.stateElement.states,
                builder.drawable ?: builder.drawableBuilder?.invoke()
            )
        }
        val defaultDrawable = default ?: defaultBuilder?.invoke()
        if (defaultDrawable != null) {
            stateListDrawable.addState(intArrayOf(), defaultDrawable)
        }
        return stateListDrawable
    }

    class StateBuilder(val stateElement: StateElement) {
        var drawable: Drawable? = null
        var drawableBuilder: (() -> Drawable)? = null

        fun drawable(builder: () -> Drawable): StateBuilder {
            this.drawableBuilder = builder
            return this
        }

        fun drawable(drawable: Drawable): StateBuilder {
            this.drawable = drawable
            return this
        }

    }

}
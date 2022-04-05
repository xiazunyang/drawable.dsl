package cn.numeron.drawable.dsl

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

class StateListBuilder {

    private var default: Drawable? = null
    private var defaultBuilder: (() -> Drawable)? = null
    private var builders = mutableListOf<StateBuilder>()

    fun setDefault(drawable: Drawable): StateListBuilder {
        this.default = drawable
        return this
    }

    fun setDefault(builder: () -> Drawable): StateListBuilder {
        defaultBuilder = builder
        return this
    }

    fun addState(state: StateElement, drawable: Drawable): StateListBuilder {
        val stateBuilder = StateBuilder(state)
        stateBuilder.drawable(drawable)
        builders.add(stateBuilder)
        return this
    }

    fun addState(state: StateElement, builder: () -> Drawable): StateListBuilder {
        val stateBuilder = StateBuilder(state)
        stateBuilder.drawable(builder)
        builders.add(stateBuilder)
        return this
    }

    fun build(): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        for (builder in builders) {
            stateListDrawable.addState(
                builder.stateElement.asStates(),
                builder.drawable ?: builder.drawableBuilder?.invoke()
            )
        }
        stateListDrawable.addState(intArrayOf(), default ?: defaultBuilder?.invoke())
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
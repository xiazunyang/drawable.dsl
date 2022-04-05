package cn.numeron.drawable.dsl

import android.content.res.ColorStateList

class ColorStateBuilder {

    var default: Int? = null
    private var defaultBuilder: (() -> Int)? = null

    private val builders = mutableListOf<StateBuilder>()

    fun setDefault(builder: () -> Int): ColorStateBuilder {
        defaultBuilder = builder
        return this
    }

    fun build(): ColorStateList {
        val (states, colors) = builders.map {
            val color = it.color ?: it.colorBuilder?.invoke() ?: throw NullPointerException()
            it.stateElement.asStates() to color
        }.unzip()
        val toTypedArray = states.toTypedArray()
        val toIntArray = colors.toIntArray()
        return ColorStateList(toTypedArray, toIntArray)
    }

    class StateBuilder(val stateElement: StateElement) {
        var color: Int? = null
        internal var colorBuilder: (() -> Int)? = null

        fun color(builder: () -> Int) {
            colorBuilder = builder
        }

    }

}
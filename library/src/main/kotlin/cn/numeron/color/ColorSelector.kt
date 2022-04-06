package cn.numeron.color

import android.content.res.ColorStateList
import cn.numeron.drawable.StateElement

class ColorSelectorBuilder {

    var defColor: Int? = null
    private var defColorBuilder: (() -> Int)? = null

    private val builders = mutableListOf<StateBuilder>()

    fun defState(color: Int): ColorSelectorBuilder {
        defColor = color
        return this
    }

    fun defState(builder: () -> Int): ColorSelectorBuilder {
        defColorBuilder = builder
        return this
    }

    fun addState(stateElement: StateElement, color: Int): ColorSelectorBuilder {
        val stateBuilder = StateBuilder(stateElement)
        stateBuilder.color = color
        builders.add(stateBuilder)
        return this
    }

    fun addState(stateElement: StateElement, builder: () -> Int): ColorSelectorBuilder {
        val stateBuilder = StateBuilder(stateElement)
        stateBuilder.color(builder)
        builders.add(stateBuilder)
        return this
    }

    fun build(): ColorStateList {
        val stateList = mutableListOf<IntArray>()
        val colorList = mutableListOf<Int>()
        for (b in builders) {
            val color = b.color ?: b.colorBuilder?.invoke() ?: throw NullPointerException()
            stateList.add(b.stateElement.states)
            colorList.add(color)
        }
        val defColor = defColor ?: defColorBuilder?.invoke()
        if (defColor != null) {
            stateList.add(intArrayOf())
            colorList.add(defColor)
        }
        return ColorStateList(stateList.toTypedArray(), colorList.toIntArray())
    }

    class StateBuilder(val stateElement: StateElement) {
        var color: Int? = null
        internal var colorBuilder: (() -> Int)? = null

        fun color(builder: () -> Int) {
            colorBuilder = builder
        }

    }

}
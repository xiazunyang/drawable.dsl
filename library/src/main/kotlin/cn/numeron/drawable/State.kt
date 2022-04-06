package cn.numeron.drawable

import kotlin.math.absoluteValue

/** 表示一种控件的状态 */
interface StateElement {

    val states: IntArray

    operator fun plus(other: SingleStateElement): StateElement

}

/** 表示一种不可变的控件状态 */
interface SingleStateElement : StateElement {

    val state: Int

    override val states: IntArray
        get() = intArrayOf(state)

    operator fun not(): SingleStateElement = VariationalState(-state)

    override operator fun plus(other: SingleStateElement): StateElement {
        return if (other.state.absoluteValue == state.absoluteValue) {
            other
        } else {
            CombinedState(other.state, this.state)
        }
    }

}

/** 表示多种控件的状态 */
interface CombinedStateElement : StateElement {

    override val states: IntArray

    override fun plus(other: SingleStateElement): StateElement {
        val otherAbsValue = other.state.absoluteValue
        val index = states.indexOfFirst {
            it.absoluteValue == otherAbsValue
        }
        return if (index == -1) {
            CombinedState(other.state, *states)
        } else {
            val newStates = states.copyOf()
            newStates[index] = other.state
            CombinedState(*newStates)
        }
    }

}

/** 表示一种已改变的状态 */
private class VariationalState(override val state: Int) : SingleStateElement

/** 表示多种状态 */
private class CombinedState(override vararg val states: Int) : CombinedStateElement

/** 预设的可用状态 */
enum class State(override val state: Int) : SingleStateElement {

    FOCUSED(android.R.attr.state_focused),
    WINDOW_FOCUSED(android.R.attr.state_window_focused),
    ENABLED(android.R.attr.state_enabled),
    CHECKABLE(android.R.attr.state_checkable),
    CHECKED(android.R.attr.state_checked),
    SELECTED(android.R.attr.state_selected),
    PRESSED(android.R.attr.state_pressed),
    ACTIVATED(android.R.attr.state_activated),
    ACTIVE(android.R.attr.state_active),
    Single(android.R.attr.state_single),
    FIRST(android.R.attr.state_first),
    MIDDLE(android.R.attr.state_middle),
    LAST(android.R.attr.state_last),
    ACCELERATED(android.R.attr.state_accelerated),
    HOVERED(android.R.attr.state_hovered),
    DRAG_CAN_ACCEPT(android.R.attr.state_drag_can_accept),
    DRAG_HOVERED(android.R.attr.state_drag_hovered);

}
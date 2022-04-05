package cn.numeron.drawable.dsl

/** 表示一种控件的状态 */
interface StateElement {

    operator fun not(): SingleStateElement

    operator fun plus(state: SingleStateElement): MultiStateElement

    fun asStates(): IntArray

}

/** 表示多种控件的状态 */
interface MultiStateElement : StateElement {

    val value: IntArray

    override fun not(): SingleStateElement {
        throw IllegalStateException("Multiple state element not support not operation.")
    }

    override fun plus(state: SingleStateElement): MultiStateElement {
        return MultipleState(intArrayOf(state.value, *value))
    }

}

/** 表示一种不可变的控件状态 */
interface SingleStateElement : StateElement {

    val value: Int

    override fun plus(state: SingleStateElement): MultiStateElement {
        return MultipleState(intArrayOf(state.value, value))
    }

    override fun not(): SingleStateElement {
        return VariationalState(-value)
    }

    override fun asStates(): IntArray = intArrayOf(value)

}

/** 表示一种已改变的状态 */
private class VariationalState(override val value: Int) : SingleStateElement

/** 表示多种状态 */
private class MultipleState(override val value: IntArray) : MultiStateElement {
    override fun asStates(): IntArray = value
}

enum class State(override val value: Int) : SingleStateElement {

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
package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position

/**
 * A mouse event triggered by a [Component].
 */
data class MouseComponentEvent(
        override val type: MouseEventType,
        val button: Int,
        /**
         * Position of this event relative to the top left corner of the tile grid/screen.
         */
        val position: Position,
        /**
         * The [Component] that triggered this event.
         */
        val source: Component
) : UIEvent {
    /**
     * The position of this event inside the [source] component. This position is relative to
     * the top left corner of [source]'s absolutePosition.
     *
     * @see Component.absolutePosition
     */
    val positionInSource: Position
        get() = position - source.absolutePosition
}

//fun MouseEvent.toMouseComponentEvent(source: Component) =
//        MouseComponentEvent(
//                type,
//                button,
//                position,
//                source
//        )
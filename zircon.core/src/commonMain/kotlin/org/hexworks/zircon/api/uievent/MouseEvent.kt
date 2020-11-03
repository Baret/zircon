package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position

data class MouseEvent<out T>(
        override val type: MouseEventType,
        val button: Int,
        val position: Position,
        val source: T? = null
) : UIEvent

val MouseEvent<Component>.positionInComponent: Position
    get() = position - (source?.absolutePosition ?: Position.zero())

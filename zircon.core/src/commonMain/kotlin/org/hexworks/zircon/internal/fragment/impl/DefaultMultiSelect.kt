package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType

class DefaultMultiSelect<T: Any>(
        width: Int,
        private val values: List<T>,
        private val callback: (newValue: T) -> Unit,
        private val centeredText: Boolean = true,
        private val toStringMethod: (T) -> String = Any::toString
) : MultiSelect<T> {

    private val indexProperty = createPropertyFrom(0)

    private val rightButton = Components.
            button().
            withText(Symbols.ARROW_RIGHT.toString()).
            withDecorations().
            build().
            apply { processComponentEvents(ComponentEventType.ACTIVATED) { nextValue()} }

    private val leftButton = Components.
            button().
            withText(Symbols.ARROW_LEFT.toString()).
            withDecorations().
            build().
            apply { processComponentEvents(ComponentEventType.ACTIVATED) { prevValue()} }

    private val label = Components.label().
            withSize(width - (leftButton.width + rightButton.width), 1).
            build()

    override val root = Components.hbox().
            withSize(width, 1).
            withSpacing(0).
            build().
            apply {
                addComponent(leftButton)
                addComponent(label)
                addComponent(rightButton)

                label.
                        apply {
                            text = getStringValue(0)
                            textProperty.updateFrom(indexProperty) { i -> getStringValue(i) }
                        }
            }

    private fun setValue(index: Int) {
        indexProperty.value = index
        callback.invoke(values[indexProperty.value])
    }

    private fun nextValue() {
        var nextIndex = indexProperty.value + 1
        if(nextIndex >= values.size) {
            nextIndex = 0
        }
        setValue(nextIndex)
    }

    private fun prevValue() {
        var prevIndex = indexProperty.value - 1
        if(prevIndex < 0) {
            prevIndex = values.size - 1
        }
        setValue(prevIndex)
    }

    private fun getStringValue(index: Int) = toStringMethod.invoke(values[index]).centered()

    private fun String.centered(): String {
        val maxWidth = label.contentSize.width
        return if (centeredText && length < maxWidth) {
            val spacesCount = (maxWidth - length) / 2
            this.padStart(spacesCount + length)
        } else {
            this.substring(0, kotlin.math.min(length, maxWidth))
        }
    }
}

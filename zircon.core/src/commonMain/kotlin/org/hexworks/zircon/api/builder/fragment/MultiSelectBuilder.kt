package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect

/**
 * Builder for a [MultiSelect]
 */
class MultiSelectBuilder<T : Any>(val width: Int, val values: List<T>) : FragmentBuilder<MultiSelect<T>, MultiSelectBuilder<T>> {

    private val log = LoggerFactory.getLogger(this::class)

    private var callback: (T) -> Unit = {log.warn("No callback defined for a MultiSelect input!")}
    private var centeredText = true
    private var toStringMethod: (T) -> String = Any::toString

    /**
     * The callback to be used when the value changes.
     */
    fun withCallback(callbackFunction: (T) -> Unit) = also {
        this.callback = callbackFunction
    }

    /**
     * Whether the text on the label should be centered.
     */
    fun withCenteredText(centerText: Boolean) = also {
        this.centeredText = centerText
    }

    /**
     * The method to use for the label text if not ::toString
     */
    fun withToStringMethod(function: (T) -> String) = also {
        this.toStringMethod = function
    }

    override fun build(): MultiSelect<T> = DefaultMultiSelect(width, values, callback, centeredText, toStringMethod)

    override fun createCopy(): Builder<MultiSelect<T>> {
        return newBuilder(width, values).
                withCallback(callback).
                withCenteredText(centeredText).
                withToStringMethod(toStringMethod)
    }

    companion object {
        fun <N: Any> newBuilder(width: Int, values: List<N>) = MultiSelectBuilder(width, values)
    }
}

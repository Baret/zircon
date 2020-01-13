package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect
import kotlin.reflect.KFunction1

class MultiSelectBuilder<T : Any>(val width: Int, val values: List<T>) : FragmentBuilder<MultiSelect<T>, MultiSelectBuilder<T>> {

    private val log = LoggerFactory.getLogger(this::class)

    private var callback: (T) -> Unit = {log.warn("No callback defined for a MultiSelect input!")}
    private var centeredText = true
    private var toStringMethod = Any::toString

    fun withCallback(callbackFunction: (T) -> Unit) = also {
        this.callback = callbackFunction
    }

    fun withCenteredText(centerText: Boolean) = also {
        this.centeredText = centerText
    }

    fun withToStringMethod(function: KFunction1<Any, String>) = also {
        this.toStringMethod = function
    }

    override fun build() = DefaultMultiSelect(width, values, callback, centeredText, toStringMethod)

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

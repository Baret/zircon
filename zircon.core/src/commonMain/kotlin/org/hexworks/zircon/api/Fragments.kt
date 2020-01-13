package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.fragment.MultiSelectBuilder
import kotlin.jvm.JvmStatic

object Fragments {

    @JvmStatic
    fun <M : Any> multSelect(width: Int, values: List<M>) = MultiSelectBuilder.newBuilder(width, values)

}
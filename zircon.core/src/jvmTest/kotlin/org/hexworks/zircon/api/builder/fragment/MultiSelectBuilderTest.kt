package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.*
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect
import org.junit.Test

class MultiSelectBuilderTest {
    @Test
    fun sizeAndMinWidth() {
        for(w in -1..2) {
            assertThatThrownBy {
                MultiSelectBuilder.newBuilder(w, listOf("a", "b")).build()
            }.
                hasMessageContaining("minimum width").
                isInstanceOf(IllegalStateException::class.java)
        }

        val minimalMultiSelect = MultiSelectBuilder.newBuilder(3, listOf("a", "b")).build()
        assertThat(minimalMultiSelect).
                isInstanceOf(MultiSelect::class.java)

        assertThat(minimalMultiSelect.root.size).
                isEqualTo(Size.create(3, 1))
    }

    @Test
    fun noEmptyList() {
        assertThatThrownBy {
            MultiSelectBuilder.newBuilder(10, listOf()).build()
        }.
            isInstanceOf(IllegalStateException::class.java).
            hasMessageContaining("may not be empty")
    }

    @Test
    fun textTooLong() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("veryLongWord", "b")).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("veryL")
    }

    @Test
    fun centeredText() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("6")).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("  6")
    }

    @Test
    fun uncenteredText() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("9")).
                withCenteredText(false).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("9")
    }

    @Test
    fun toStringMethod() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(10, listOf(TestClass(5))).
                withToStringMethod(TestClass::bigger).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).contains("500")
    }

    private fun getLabel(multiSelect: DefaultMultiSelect<out Any>) =
            multiSelect.root.children.first { it is Label } as Label

    private data class TestClass(val number: Int) {
        fun bigger() = "${number}00"
    }
}
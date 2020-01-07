package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A MultiSelect is a one line input to select one of multiple values of type [T].
 * It gets a list of objects you can cycle through with a left and a right button.
 * You must provide the width of the [Fragment], the list of values and a callBack method.
 * This callback will be invoked when the value changes (i.e. the uer pressed one of the buttons) and gets the new Value as parameter.
 * Optionally you can specify if the text on the label should be centered, default is true.
 * If the toString method of [T] is not well suited for the label, you can pass a different one.
 */
interface MultiSelect<T>: Fragment {

}
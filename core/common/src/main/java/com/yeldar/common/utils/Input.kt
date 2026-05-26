package com.yeldar.common.utils

import android.text.InputFilter
import android.widget.EditText

fun EditText.blockCyrillicInput() {
    val noCyrillicFilter = InputFilter { source, start, end, _, _, _ ->
        for (i in start until end) {
            val block = Character.UnicodeBlock.of(source[i])
            if (block == Character.UnicodeBlock.CYRILLIC) {
                return@InputFilter ""
            }
        }
        null
    }

    this.filters = this.filters.plus(noCyrillicFilter)
}

fun EditText.blockSpaces() {
    val filter = InputFilter { source, _, _, _, _, _ ->
        if (source.contains(" ")) {
            return@InputFilter source.toString().replace(" ", "")
        }
        null
    }
    this.filters = this.filters.plus(filter)
}
package com.tiwa.thermondo.extensions

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.showIf(condition: Boolean) {
    if (condition) this.visible()
    else this.gone()
}

fun View.visibleIf(condition: Boolean) {
    if (condition) this.visible()
    else this.invisible()
}
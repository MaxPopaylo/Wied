package ua.wied.presentation.common.utils.extensions

fun Float.toEvaluation(): Double = this.coerceIn(0f, 5f).toDouble()
fun Double.toEvaluation(): Float = this.coerceIn(0.0, 5.0).toFloat()
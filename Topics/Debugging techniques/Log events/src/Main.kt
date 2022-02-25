fun String?.capitalize(): String? {
    println("Before: $this")
    return when {
        isNullOrBlank() -> this.also { println("After: $this") }
        length == 1 -> uppercase().also { println("After: ${uppercase()}") }
        else -> this[0].uppercase() + substring(1).also { println("After: ${this[0].uppercase() + substring(1)}") }
    }
}

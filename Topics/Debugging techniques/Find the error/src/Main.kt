import java.util.Scanner

fun swapInts(ints: IntArray): IntArray {
//    println("ints[1]: ${ints[1]}")
//    println("ints[0]: ${ints[0]}")
//    println(intArrayOf(ints[1], ints[0]))
//    println(intArrayOf(ints[1], ints[0])[0])
//    println(intArrayOf(ints[1], ints[0])[1])
    return intArrayOf(ints[1], ints[0])
}

fun main() {
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        var ints = intArrayOf(
            scanner.nextLine().toInt(),
            scanner.nextLine().toInt(),
        )
//        println("ints: $ints")
        ints = swapInts(ints)
        println(ints[0])
        println(ints[1])
    }
}

fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    // put your code here
    val result = first.toMutableList()
    result.addAll(second)
    return result
}

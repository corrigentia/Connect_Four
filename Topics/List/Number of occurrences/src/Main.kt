fun solution(strings: List<String>, str: String): Int {
    // put your code here
    var occurrences = 0
    for (index in strings.indices) if (strings[index] == str) ++occurrences
    return occurrences
}
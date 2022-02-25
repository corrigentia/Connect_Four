fun solution(products: List<String>, product: String) {
    // put your code here
    for (index in products.indices) if (products[index] == product) print("$index ")
}

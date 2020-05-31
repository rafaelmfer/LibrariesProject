package rafaelmfer.customviews.extensions

fun <T> Collection<T>.get(index: Int): T {
    forEachIndexed { indexed, element -> if (indexed == index) return element }
    throw IndexOutOfBoundsException()
}

fun <T> MutableList<T>.update(collection: MutableList<T>) {
    clear()
    collection.forEach { add(it) }
}

fun <T : Comparable<T>> listOfRange(iterable: Iterable<T>): MutableList<T> {
    val list = mutableListOf<T>()
    iterable.forEach { list.add(it) }
    return list
}
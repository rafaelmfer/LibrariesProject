package rafaelmfer.customviews.extensions

fun <K : Any, V : Any> crossMapOf(keys: List<K> = listOf(), values: List<V> = listOf()) =
    CrossMap<K, V>().apply { keys.forEachIndexed { index, key -> put(key, values[index]) } }

class CrossMap<Key : Any, Value : Any> : Map<Key, Value> {

    private val invertedMap = mutableMapOf<Value, Key>()
    private val map = mutableMapOf<Key, Value>()
    override val values get() = map.values
    override val keys get() = map.keys
    override val size get() = map.size
    override val entries: Set<Map.Entry<Key, Value>> = map.entries

    fun put(key: Key, value: Value) {
        invertedMap[value] = key
        map[key] = value
    }

    override fun get(key: Key): Value? = map[key]

    fun getValue(key: Key): Value? = map[key]

    fun getKey(value: Value): Key? = invertedMap[value]

    fun remove(key: Key) {
        invertedMap.remove(map[key])
        map.remove(key)
    }

    fun removeKey(key: Key) {
        invertedMap.remove(map[key])
        map.remove(key)
    }

    fun removeValue(value: Value) {
        map.remove(invertedMap[value])
        invertedMap.remove(value)
    }

    override fun containsKey(key: Key) = map.containsKey(key)

    override fun containsValue(value: Value) = map.containsValue(value)

    override fun isEmpty() = map.isEmpty()
}
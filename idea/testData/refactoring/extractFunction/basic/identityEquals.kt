// SUGGESTED_NAMES: b, getT
// PARAM_DESCRIPTOR: value-parameter val a: kotlin.Int defined in test
// PARAM_DESCRIPTOR: value-parameter val b: kotlin.Int defined in test
// PARAM_TYPES: kotlin.Int, Number, Comparable<Int>, java.io.Serializable, Any
// PARAM_TYPES: kotlin.Int, Number, Comparable<Int>, java.io.Serializable, Any

fun test(a: Int, b: Int): Boolean {
    val t = <selection>a === b</selection>
}
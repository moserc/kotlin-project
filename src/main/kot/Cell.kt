package kot

import java.time.LocalDate

/**
 * Cell data class holding attributes for cell phones:
 * manufacturer, model, launch year, launch status, dims, weight, sim card,
 * display specs, features, OS.
 */
class Cell(
    //class attributes
    var oem: String? = null,
    var model: String? = "Unknown",
    var launch_announced: Int? = null,
    var launch_status: String? = null,
    var body_dimensions: String? = null,
    var body_weight: Float? = null,
    var body_sim: String? = null,
    var display_type: String? = null,
    var display_size: Float? = null,
    var display_resolution: String? = null,
    var features_sensors: String? = null, //todo
    var platform_os: String? = null //todo
)

//getter & setters are implicit
//create more functions
{
    /**
     * Custom display output for cell specifications.
     */
    override fun toString(): String {
        return "$oem, $model, $launch_announced, $launch_status, $body_dimensions, " +
                "$body_weight, $body_sim, $display_type, $display_size, $display_resolution, " +
                "$features_sensors, $platform_os"
    }
}

/**
 * Takes a hashmap as a parameter.
 * Prints the largest screen size.
 */
fun largestScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.display_size }
    val max = displaySizes.maxOrNull()
    println("Largest screen size: %.2f inches".format(max))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the smallest screen size.
 */
fun smallestScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.display_size }
    val min = displaySizes.minOrNull()
    println("Smallest screen size: %.2f inches".format(min))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the average screen size.
 */
fun avgScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.display_size }
    val avg = displaySizes.average()
    println("Average screen size: %.2f inches".format(avg))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the average cell weight.
 */
fun avgWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.body_weight }
    val avg = cellWeights.average()
    println("Average cell weight: %.2f grams".format(avg))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the heaviest cell weight.
 */
fun maxWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.body_weight }
    val max = cellWeights.maxOrNull()
    println("Heaviest cell weight: %.2f grams".format(max))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the lightest cell weight.
 */
fun minWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.body_weight }
    val min = cellWeights.minOrNull()
    println("Lightest cell weight: %.2f grams".format(min))
}

//today variable for use in year calcs and output
val today = LocalDate.now().year

/**
 * Takes a hashmap as a parameter.
 * Prints the oldest cell age.
 */
fun maxAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launch_announced }.filter { it in 1990..<today }
    val max = cellAges.maxOrNull()
    println("Most recent cell: launched %d years ago".format(today - max!!))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the newest cell age.
 */
fun minAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launch_announced }.filter { it in 1990..<today }
    val min = cellAges.minOrNull()
    println("Oldest cell: launched %d years ago".format(today - min!!))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the average cell age.
 */
fun avgAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launch_announced }.filter { it in 1990..<today }
    val avg = today - cellAges.average()
    println("Average cell age: %.2f years".format(avg))
}

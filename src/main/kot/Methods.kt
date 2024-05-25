package kot

import java.time.LocalDate
import kotlin.math.max

/*-------------------SCREEN SIZE METHODS-------------------*/
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

/*-------------------PHONE WEIGHT METHODS-------------------*/

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
 * Takes a hashmap as a parameter. Returns a double representation of the average.
 * This function works in conjunction with the avgWeightByCompany function.
 */
fun avgWeight(nonNullWeights: Map<String, Float>): Double {
    val cellWeights = nonNullWeights.values.toList()
    return cellWeights.average()
}

/**
 * Takes a hashmap and a company name as a parameter.
 * Returns the average cell weight for the specified company.
 */
fun avgWeightByCompany(cellMap: HashMap<String, Cell>, company: String): Double {

    val weights = HashMap<String, Float?>()
    for ((key, cell) in cellMap) {
        if (cell.oem == company && cell.launch_status?.contains("Released\\s\\d{4}") != false && cell.body_weight != null) {
            weights[key] = cell.body_weight!!
        }
    }

    val nonNullWeights = weights.filterValues { it != null }.mapValues { it.value!! }
    val avg = avgWeight(nonNullWeights)

    if (nonNullWeights.isEmpty()) {
        return 0.0
    }

    //uncomment the line below for each company's average phone weight
    //println("Average cell weight for $company is: %.2f grams".format(avg)) //print statement for reporting avg weight per company
    return avg
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

/*-------------------YEAR METHODS-------------------*/

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

/**
 * Takes a hashmap as a parameter.
 * Returns the years' release count.
 */
fun getCount(cellMap: HashMap<String, Cell>) {
    val statusRegex = Regex("""Released\s(\d{4})""")

    //gather the list of unique years, check for 'Released' status
    val releasedYears = cellMap.values.filter { cell ->
        cell.launch_status?.let { status -> statusRegex.containsMatchIn(status ?: "") } == true
    }
        .mapNotNull { it.launch_status }.filter { it > "1999" }.toSet()

    val perYear = releasedYears.associateWith { year ->
        cellMap.values
            .count { it.launch_status == year }
    }
    val maxCount = perYear.maxByOrNull { it.value }
    val maxCountSplit = maxCount?.key?.split(" ")
    val maxCountYear = maxCountSplit?.get(1)
    val maxCountVal = maxCount!!.value
    println("Answer: Since 1999, the most phones released in a single year was in $maxCountYear with $maxCountVal models released.\n")
}

fun misMatched(cellMap: HashMap<String, Cell>) {
    val releasedRegex = Regex("""Released\s(\d{4})""")
    for ((_, cell) in cellMap) {
        if (cell.launch_status?.let { releasedRegex.containsMatchIn(it) } == true) {
            val releasedMatch = releasedRegex.find(cell.launch_status!!)
            val releasedYear = releasedMatch?.groupValues?.get(1)?.toIntOrNull()
            val launchYear = cell.launch_announced
            if (releasedYear != null) {
                if (releasedYear != null && launchYear != null && releasedYear != launchYear) {
                    println("${cell.oem} model ${cell.model}")
                }
            }
        }
    }
}
/*-------------------OTHER METHODS-------------------*/
fun singleSensor(cellMap: HashMap<String, Cell>){
    var count = 0
    for ((_, cell) in cellMap) {
        if (cell.features_sensors != null) {
            val sensors = cell.features_sensors!!.split(",")
            if (sensors.size == 1 && sensors.contains("Accelerometer")) {
                count++
            }
        }
    }
    println("Answer: There are $count phones with a single sensor listed in its features\n")
}

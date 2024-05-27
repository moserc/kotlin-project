package kot

import java.time.LocalDate

/* Page author: Cheryl Moser */

//today variable for use in year calculations and output
val today = LocalDate.now().year

/*-------------------SCREEN SIZE METHODS-------------------*/
/**
 * Takes a hashmap as a parameter.
 * Prints the largest screen size.
 * @param cellMap hashmap of cell model data.
 */
fun largestScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.displaySize }
    val max = displaySizes.maxOrNull()
    println("Largest screen size: %.2f inches".format(max))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the smallest screen size.
 * @param cellMap hashmap of cell model data.
 */
fun smallestScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.displaySize }
    val min = displaySizes.minOrNull()
    println("Smallest screen size: %.2f inches".format(min))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the average screen size.
 * @param cellMap hashmap of cell model data.
 */
fun avgScreen(cellMap: HashMap<String, Cell>) {
    val displaySizes = cellMap.values.mapNotNull { it.displaySize }
    val avg = displaySizes.average()
    println("Average screen size: %.2f inches".format(avg))
}

/*-------------------PHONE WEIGHT METHODS-------------------*/

/**
 * Takes a hashmap as a parameter.
 * Prints the average cell weight.
 * @param cellMap hashmap of cell model data.
 */
fun avgWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.weight }
    val avg = cellWeights.average()
    println("Average cell weight: %.2f grams".format(avg))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the heaviest cell weight.
 * @param cellMap hashmap of cell model data.
 */
fun maxWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.weight }
    val max = cellWeights.maxOrNull()
    println("Heaviest cell weight: %.2f grams".format(max))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the lightest cell weight.
 * @param cellMap hashmap of cell model data.
 */
fun minWeight(cellMap: HashMap<String, Cell>) {
    val cellWeights = cellMap.values.mapNotNull { it.weight }
    val min = cellWeights.minOrNull()
    println("Lightest cell weight: %.2f grams".format(min))
}

/*-------------------YEAR METHODS-------------------*/

/**
 * Takes a hashmap as a parameter.
 * Prints the oldest cell age.
 * @param cellMap hashmap of cell model data.
 */
fun maxAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launchYear }.filter { it in 1990..<today }
    val max = cellAges.maxOrNull()
    println("Most recent cell: launched %d years ago".format(today - max!!))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the newest cell age.
 * @param cellMap hashmap of cell model data.
 */
fun minAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launchYear }.filter { it in 1990..<today }
    val min = cellAges.minOrNull()
    println("Oldest cell: launched %d years ago".format(today - min!!))
}

/**
 * Takes a hashmap as a parameter.
 * Prints the average cell age.
 * @param cellMap hashmap of cell model data.
 */
fun avgAge(cellMap: HashMap<String, Cell>) {
    val cellAges = cellMap.values.mapNotNull { it.launchYear }.filter { it in 1990..<today }
    val avg = today - cellAges.average()
    println("Average cell age: %.2f years".format(avg))
}

/*-------------------METHODS FOR REPORT QUESTIONS-------------------*/

/**
 * Takes a hashmap as a parameter.
 * Returns a hashmap containing the average cell weight for each company.
 * @param cellMap hashmap of cell model data.
 * @return hashmap containing average cell weight per company.
 */
fun avgWeightByCompany(cellMap: HashMap<String, Cell>): Map<String, Double> {

    val companyAverages = mutableMapOf<String, Double>()

    for (company in cellMap.values.mapNotNull { it.oem }.toSet()) { //toSet to isolate unique company names
        //fill 'weights' with company name and weight
        val weights = cellMap.filter { it.value.oem == company && it.value.weight != null }
            .mapValues { it.value.weight!! }

        if (weights.isNotEmpty()) {
            //put the weight values into a list
            val avg = weights.values.toList().average()
            //add to companyAverages map, key = company : value = avg
            companyAverages[company] = avg
        }

        //uncomment the line below to see all company averages
        //println("Average cell weight for $company is: %.2f grams".format(avg))
    }//end for

    return companyAverages
}

/**
 * Checks for mismatches in launch announced year and actual release year.
 * Takes a hashmap as a parameter.
 * @param cellMap hashmap of cell model data.
 */
fun misMatched(cellMap: HashMap<String, Cell>) {
    val releasedRegex = Regex("""Released\s(\d{4})""")
    println("Answer: Mismatches between year announced and year released were found in the following models:\n")
    var count = 1
    for ((_, cell) in cellMap) {
        //find regex matches in releaseStatus
        if (cell.releaseStatus?.let { releasedRegex.containsMatchIn(it) } == true) {
            val releasedMatch = releasedRegex.find(cell.releaseStatus!!) //ensure not null

            //grab the year (there is only one grouping in the regex)
            val releasedYear = releasedMatch?.groupValues?.get(1)?.toIntOrNull()

            //ensure released year and launched year are not null, check for mismatch
            if (releasedYear != null && cell.launchYear != null && releasedYear != cell.launchYear) {
                println("    $count) ${cell.oem}, model: ${cell.model}. Announced ${cell.launchYear}, ${cell.releaseStatus}")
                count++
            }
        }
    }
}

/**
 * Takes a hashmap as a parameter. Outputs the count of all released models
 * with a single sensor listed.
 * @param cellMap hashmap of cell model data.
 */
fun singleSensor(cellMap: HashMap<String, Cell>) {
    var count = 0
    for ((_, cell) in cellMap) {
        if (cell.sensors != null) {
            //split each 'sensors' element using its internal comma as a delimiter
            val sensors = cell.sensors!!.split(",")
            /*increment the count if the new list is of size 1 and contains the word
            "Accelerometer". Without the latter constraint, output included various
            operating systems, or "V1". After reviewing that list, Accelerometer was the only
            valid sensor output within single-sensor listings.*/
            if (sensors.size == 1 && sensors.contains("Accelerometer")) {
                count++
            }
        }
    }
    println("Answer: There are $count phones with a single sensor listed in its features.")
}

/**
 * Takes a hashmap as a parameter.
 * Calculates year mismatches between launch announce, and actual release years.
 * @param cellMap hashmap of cell model data.
 */
fun getCount(cellMap: HashMap<String, Cell>) {
    val statusRegex = Regex("""Released\s(\d{4})""")

    //gather the list of unique years, check for 'Released' status
    val releasedYears = cellMap.values.filter { cell ->
        cell.releaseStatus?.let { status -> statusRegex.containsMatchIn(status) } == true //check for "released" status
    }.mapNotNull { it.releaseStatus }.filter { it > "1999" }.toSet() //only return unique years greater than 1999

    val perYear = releasedYears.associateWith { year -> //year = key in new map
        cellMap.values.count { it.releaseStatus == year } //count releases in the given year
    }

    val maxCount = perYear.maxByOrNull { it.value } //grab the max count of all years
    val maxCountSplit = maxCount?.key?.split(" ") //split "Released" from the year
    val maxCountYear = maxCountSplit?.get(1) //grab the year
    val maxCountVal = maxCount!!.value //grab the max count value
    println("Answer: Since 1999, the most phones released in a single year was in $maxCountYear with $maxCountVal models released.")
}

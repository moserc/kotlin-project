package kot

/**
 * Driver class for analyzing the cleaned data and outputting results.
 * @author Cheryl Moser
 */
fun main() {

    val fileName = "./src/main/resources/cells.csv" //file to read in
    val cellMap = HashMap<String, Cell>() //store objects in a hashmap
    CSVReader(fileName, cellMap)

    println("\n-------------------BEGIN OUTPUT-------------------\n")
    val numResults = cellMap.count()
    println("Found $numResults unique results by model")

    //uncomment the line below to print the objects in the hashmap
    //cellMap.forEach { (key, cell) -> println(cell.toString())}

    println("\n-------------------OVERALL SCREEN STATS-------------------\n")
    largestScreen(cellMap)
    smallestScreen(cellMap)
    avgScreen(cellMap)

    println("\n-------------------OVERALL WEIGHT STATS-------------------\n")
    maxWeight(cellMap)
    minWeight(cellMap)
    avgWeight(cellMap)

    println("\n-------------------OVERALL AGE STATS-------------------\n")
    maxAge(cellMap)
    minAge(cellMap)
    avgAge(cellMap)

    println("\n-------------------QUESTION/ANSWER #1-------------------\n")
    println("Question: What company (oem) has the highest average weight of the phone body?\n")
    val companies = avgWeightByCompany(cellMap)
    val maxResult = companies.values.maxOrNull() ?: 0.0
    val company = companies.filterValues { it == maxResult }.keys.first()
    println("Answer: $company has the highest average weight of phone body at %.2f grams.".format(maxResult))

    println("\n-------------------QUESTION/ANSWER #2-------------------\n")

    println("Question: Were there any phones that were announced in one year and released in another? What are they? Give me the oem and models.\n")
    misMatched(cellMap)

    println("\n-------------------QUESTION/ANSWER #3-------------------\n")

    println("Question: How many phones have only one feature sensor?\n")
    singleSensor(cellMap)

    println("\n-------------------QUESTION/ANSWER #4-------------------\n")

    println("Question: What year had the most phones launched in any year later than 1999?\n")
    getCount(cellMap)
    println("\n-------------------END OUTPUT-------------------\n")
}//end main

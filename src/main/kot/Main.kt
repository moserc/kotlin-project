package kot

import java.io.File

fun main() {

    val fileName = "./src/main/resources/cells.csv" //file to read in
    val cellMap = HashMap<String, Cell>() //store objects in a hashmap

    //read in the csv file
    File(fileName).readLines().forEach { line ->
        val cell = makeCellObject(line) //clean the data
        //only add to the hashmap if the model doesn't already exist there
        if (!cellMap.containsKey(cell.model))
            cellMap[cell.model!!] = cell
        //remove line containing column names
        if (cellMap.containsKey("model")) {
            cellMap.remove("model")
        }
    }//end readLines

    //uncomment the line below to print the objects in the hashmap
    //cellMap.forEach { (key, cell) -> println(cell.toString())}

    //output for additional methods
    println()
    println("-------------------BEGIN OUTPUT-------------------")
    println()
    println("-------------------screen stats-------------------")
    largestScreen(cellMap)
    smallestScreen(cellMap)
    avgScreen(cellMap)

    println()
    println("-------------------weight stats-------------------")
    maxWeight(cellMap)
    minWeight(cellMap)
    avgWeight(cellMap)

    println()
    println("-------------------age stats-------------------")
    maxAge(cellMap)
    minAge(cellMap)
    avgAge(cellMap)

    println()

    println("-------------------QA #1-------------------")
    println("Question: What company (oem) has the highest average weight of the phone body?")
    val companies = ArrayList<String>()
    val results = ArrayList<Double>()

    companies.addAll(cellMap.values.mapNotNull { it.oem }.toSet()) //gather the list of unique company names
    for (company in companies) {
        results.add(avgWeightByCompany(cellMap, company)) //get the average weight per company, put it in results list
    }
    val maxResult = results.max() //grab the highest average
    val maxResultIndex = results.indexOf(maxResult) //grab the index of the highest average
    val company = companies[maxResultIndex] //should be the same index in the companies list
    println("Answer: $company has the highest average weight of phone body at %.2f grams.\n".format(maxResult))

    println("-------------------QA #2-------------------")

    println("Question: Were there any phones that were announced in one year and released in another? What are they? Give me the oem and models.")
    println("Answer: Mismatches between year announced and year released were found in the following:")
    misMatched(cellMap)
    println()

    println("-------------------QA #3-------------------")

    println("Question: How many phones have only one feature sensor?")
    singleSensor(cellMap)

    println("-------------------QA #4-------------------")

    println("Question: What year had the most phones launched in any year later than 1999?")
    getCount(cellMap)
    println("-------------------END OUTPUT-------------------")
}//end main

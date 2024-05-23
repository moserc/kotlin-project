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
        if (cellMap.containsKey("model")){
            cellMap.remove("model")
        }
    }//end readLines

    //print the objects in the hashmap
    cellMap.forEach { (key, cell) ->
        println(cell.toString())
    }

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
    println("-------------------QA-------------------")

    //What company (oem) has the highest average weight of the phone body?
    //get average weight by company
    //return the oem of the one with the max avg

    //Was there any phones that were announced in one year and released in another? What are they? Give me the oem and models.
    //Isolate phones with year mismatch (launch_announced vs launch_status==released + launch_status year)

    //How many phones have only one feature sensor?
    //split feature sensor element
    //return count of all with size = 1

    //What year had the most phones launched in any year later than 1999?
    //Isolate phones by launch_status = released + year > 1999
    //return count for most lines of same year

    println()
    println("-------------------END OUTPUT-------------------")

}//end main

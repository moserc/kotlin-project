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
    println("-------------------END OUTPUT-------------------")
}//end main

package kot

import java.io.File

/**
 * Class for reading-in data from csv.
 * @author Cheryl Moser
 */
class CSVReader(fileName: String, cellMap: HashMap<String, Cell>) { //primary constructor

    init {
        readIn(fileName, cellMap)
    }

    /**
     * Function for reading in data from csv.
     * @param fileName the name of the file to be read.
     * @param cellMap the hashmap for cell model data.
     * @return the cellMap containing keys = model and values = Cell object
     */
    private fun readIn(fileName: String, cellMap: HashMap<String, Cell>): HashMap<String, Cell> {

        //read in the csv file
        val lines = File(fileName).readLines()

        lines.drop(1).forEach { line ->
            val cell = makeCellObject(line) //clean the data
            //only add to the hashmap if the model doesn't already exist there
            if (!cellMap.containsKey(cell.model))
                cellMap[cell.model!!] = cell
        }//end readIn

        return cellMap
    }
}

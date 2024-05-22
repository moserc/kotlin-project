package kot

import java.io.File

/**
 * Cleans data and converts lines to objects.
 */
fun makeCellObject(line: String): Cell {
    //split the string using custom parser
    val cleanData = parse(line).map { item ->
        //replace "-" items with null
        if (item == "-" || item.isEmpty()) "null" else item.trim()
    }

    //cleaning task for launch_announced: capture year
    val yearRegex = Regex("""\d{4}""")
    val matchYear = yearRegex.find(line)
    val launch_announced: Int? = matchYear?.value?.toIntOrNull()

    //cleaning task for launch_status: capture one of 3 words
    val launchRegex = Regex("(Discontinued|Cancelled|Released)")
    val matchStatus = launchRegex.find(line)
    val launch_status = matchStatus?.value

    //cleaning task for body_weight: capture number before "g"
    val weightRegex = Regex("""\d+\s(?=g)""")
    val matchWeight = weightRegex.find(line)
    val body_weight = matchWeight?.value?.toFloatOrNull()

    //cleaning task for body_sim: capture all but yes or no
    val body_sim = cleanData.getOrNull(6)
    val execptYesNo = when (body_sim?.lowercase()) {
        "yes", "no" -> null
        else -> body_sim
    }

    //cleaning task for display_size: capture number before inches
    val sizeRegex = Regex("""\d+\s(?=inches)""")
    val matchSize = sizeRegex.find(line)
    val display_size = matchSize?.value?.toFloatOrNull()

    //catch elements that only consist of numbers
    val onlyNumsBad = Regex("""^(?![0-9]+${'$'}).*""")
    val matchNumsBad = onlyNumsBad.find(line)

    //cleaning task for features_sensors
    val features_sensors = cleanData.getOrNull(10)
    val exceptNumsSensors =
        if (matchNumsBad != null && matchNumsBad.value == features_sensors) {
            null
        } else {
            features_sensors
        }

    //cleaning task for platform_os
    val maybeOS = cleanData.getOrNull(11)?.split(",")
    val platform_os = maybeOS?.get(0)
    val exceptNumsOS =
        if (matchNumsBad != null && matchNumsBad.value == platform_os) {
            null
        } else {
            platform_os
        }

    return Cell(
        oem = cleanData.getOrNull(0),
        model = cleanData.getOrNull(1),
        launch_announced,
        launch_status,
        body_dimensions = cleanData.getOrNull(4),
        body_weight,
        body_sim = execptYesNo,
        display_type = cleanData.getOrNull(7),
        display_size,
        display_resolution = cleanData.getOrNull(9),
        features_sensors = exceptNumsSensors,
        platform_os = exceptNumsOS
    )
}

/**
 * Parses the input such that commas are the delimiter except in cases when they
 * are contained within quoted text.
 */
fun parse(line: String): List<String> {
    val pattern = """("([^"]*)")|([^,]+)""".toRegex()
    val result = mutableListOf<String>()

    pattern.findAll(line).forEach { matchResult ->
        val quotedText = matchResult.groups[2]?.value ?: matchResult.value
        result.add(quotedText.trim().trim('"'))
    }//end regex matching

    return result
}//end parse

fun main() {
    val fileName = "./src/main/resources/cells.csv"
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
}//end main

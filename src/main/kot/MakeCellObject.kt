package kot

/**
 * Cleans data and converts each line to an object.
 * @param line input string from csv read-in
 * @author Cheryl Moser
 */
fun makeCellObject(line: String): Cell {
    //split the string using custom parser
    val cleanData = parse(line).map { item ->
        //replace "-" items with null
        if (item == "-" || item.isBlank() || item.isEmpty()) null else item.trim()
    }

    //cleaning task for launchYear: capture year
    val yearRegex = Regex("""(\d{4})""")
    val matchYear = cleanData.getOrNull(2)?.let { yearRegex.find(it) }
    val launchYearInt: Int? = matchYear?.groups?.get(1)?.value?.toIntOrNull()
    val launchYear =
        if (launchYearInt in 1995..today) {
            launchYearInt
        } else {
            null
        }

    //cleaning task for releaseStatus: capture one of 3 words
    val launchRegex = Regex("(Discontinued|Cancelled|Released\\s\\d{4})")
    val matchStatus = cleanData.getOrNull(3)?.let { launchRegex.find(it) }
    val releaseStatus = matchStatus?.value

    //cleaning task for weight: capture number before "g"
    val weightRegex = Regex("""(\d+.?\d?+)\s(?=g)""")
    val matchWeight = cleanData.getOrNull(5)?.let { weightRegex.find(it) }
    val weight = matchWeight?.groupValues?.get(1)?.toFloatOrNull()

    //cleaning task for sim: capture all but yes or no
    val sim = cleanData.getOrNull(6)
    val exceptYesNo = when (sim?.lowercase()) {
        "yes", "no" -> null
        else -> sim
    }

    //cleaning task for displaySize: capture number before inches
    val sizeRegex = Regex("""(\d+.?\d?+)\s?(?=inches)""")
    val matchSize = cleanData.getOrNull(8)?.let { sizeRegex.find(it) }
    val displaySize = matchSize?.groupValues?.get(1)?.toFloatOrNull()

    //cleaning task for resolution: capture all before pixels
    val resRegex = Regex("""(\d+\s*x\s*\d+)\s*pixels""")
    var idx = -1
    for (i in cleanData.indices) {
        if (cleanData[i]?.contains("pixels") == true) {
            idx = i
        }
    }
    val resMatch = cleanData.getOrNull(idx)?.let {
        resRegex.find(it)
    }
    val resolution = resMatch?.groupValues?.get(1)?.trim()

    //cleaning task for sensors -> make sure it's not all numbers
    val onlyNums = Regex("""^\d+$""")
    idx++
    val matchNums = cleanData.getOrNull(idx)?.let { onlyNums.find(it) }
    val exceptNumsSensors =
        if (matchNums == null) {
            cleanData.getOrNull(idx)
        } else {
            null
        }

    //cleaning task for os -> grab text before comma, make sure it's not all numbers
    val matchNumsOS = cleanData.getOrNull(11)?.let { onlyNums.find(it) }
    val exceptNumsOS =
        if (matchNumsOS == null) {
            cleanData.getOrNull(11)?.split(",")?.get(0)
        } else {
            null
        }

    return Cell(
        oem = cleanData.getOrNull(0),
        model = cleanData.getOrNull(1),
        launchYear = launchYear,
        releaseStatus = releaseStatus,
        dims = cleanData.getOrNull(4),
        weight = weight,
        sim = exceptYesNo,
        displayType = cleanData.getOrNull(7),
        displaySize = displaySize,
        resolution = resolution,
        sensors = exceptNumsSensors,
        os = exceptNumsOS
    )
}

package kot

/**
 * Cleans data and converts each line to an object.
 * @author Cheryl Moser
 */
fun makeCellObject(line: String): Cell {
    //split the string using custom parser
    val cleanData = parse(line).map { item ->
        //replace "-" items with null
        if (item == "-" || item.isBlank()) null else item.trim()
    }

    //cleaning task for launchYear: capture year
    val yearRegex = Regex("""(\d{4})""")
    val matchYear = yearRegex.find(line)
    val launchYearInt: Int? = matchYear?.groups?.get(1)?.value?.toIntOrNull()
    val launchYear =
        if (launchYearInt in 1995..today) {
            launchYearInt
        } else {
            null
        }

    //cleaning task for releaseStatus: capture one of 3 words
    val launchRegex = Regex("(Discontinued|Cancelled|Released\\s\\d{4})")
    val matchStatus = launchRegex.find(line)
    val releaseStatus = matchStatus?.value

    //cleaning task for weight: capture number before "g"
    val weightRegex = Regex("""\d+.\d+\s(?=g)""")
    val matchWeight = weightRegex.find(line)
    val weight = matchWeight?.value?.toFloatOrNull()

    //cleaning task for sim: capture all but yes or no
    val sim = cleanData.getOrNull(6)
    val exceptYesNo = when (sim?.lowercase()) {
        "yes", "no" -> null
        else -> sim
    }

    //cleaning task for displaySize: capture number before inches
    val sizeRegex = Regex("""\d+.\d+\s(?=inches)""")
    val matchSize = sizeRegex.find(line)
    val displaySize = matchSize?.value?.toFloatOrNull()

    //catch elements that only consist of numbers
    val onlyNums = Regex("""^(?![0-9]+${'$'}).*""")
    val matchNums = onlyNums.find(line)

    //cleaning task for sensors -> make sure it's not all numbers
    val sensors = cleanData.getOrNull(10)
    val exceptNumsSensors =
        if (matchNums != null && matchNums.value == sensors) {
            null
        } else {
            sensors
        }

    //cleaning task for os -> grab text before comma, make sure it's not all numbers
    val maybeOS = cleanData.getOrNull(11)?.split(",")
    val os = maybeOS?.get(0)
    val exceptNumsOS =
        if (matchNums != null && matchNums.value == os) {
            null
        } else {
            os
        }

    return Cell(
        oem = cleanData.getOrNull(0),
        model = cleanData.getOrNull(1),
        launchYear,
        releaseStatus,
        dims = cleanData.getOrNull(4),
        weight,
        sim = exceptYesNo,
        displayType = cleanData.getOrNull(7),
        displaySize,
        resolution = cleanData.getOrNull(9),
        sensors = exceptNumsSensors,
        os = exceptNumsOS
    )
}

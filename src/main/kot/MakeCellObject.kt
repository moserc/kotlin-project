package kot

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
    val weightRegex = Regex("""\d+.\d+\s(?=g)""")
    val matchWeight = weightRegex.find(line)
    val body_weight = matchWeight?.value?.toFloatOrNull()

    //cleaning task for body_sim: capture all but yes or no
    val body_sim = cleanData.getOrNull(6)
    val exceptYesNo = when (body_sim?.lowercase()) {
        "yes", "no" -> null
        else -> body_sim
    }

    //cleaning task for display_size: capture number before inches
    val sizeRegex = Regex("""\d+.\d+\s(?=inches)""")
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
        body_sim = exceptYesNo,
        display_type = cleanData.getOrNull(7),
        display_size,
        display_resolution = cleanData.getOrNull(9),
        features_sensors = exceptNumsSensors,
        platform_os = exceptNumsOS
    )
}

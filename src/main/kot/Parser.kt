package kot

/**
 * Parses the input such that commas are the delimiter except in cases when they
 * are contained within quoted text.
 * @author Cheryl Moser
 */
fun parse(line: String): List<String> {

    //data fields for the list of results, regex pattern, call to findAll
    val result = mutableListOf<String>()
    val pattern = """("([^"]*)")|([^,]+)""".toRegex()
    val matches = pattern.findAll(line)

    //add match hits to the result string
    for (matchResult in matches) {
        val quotedText = matchResult.groups[2]?.value ?: matchResult.value
        result.add(quotedText.trim().trim('"'))
    }//end regex matching

    return result
}//end parse

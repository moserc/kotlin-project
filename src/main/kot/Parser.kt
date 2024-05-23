package kot

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

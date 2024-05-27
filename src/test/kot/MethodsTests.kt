package kot

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Test class for alternative language project.
 * @author Cheryl Moser
 */
class MethodsTests {

    /**
     * Ensures the file path is found.
     */
    @Test
    fun testFilePath() {
        val fileName = "./src/main/resources/cells.csv"
        val file = File(fileName)
        assertTrue(file.exists(), "File does not exist")
    }

    /**
     * Tests file not found.
     */
    @Test
    fun testFileDNE() {
        val fileName = "./src/main/resources/clls.csv"
        val file = File(fileName)
        assertFalse(file.exists())
    }

    /**
     * Tests that the cellMap is not empty after CSVReader is called.
     */
    @Test
    fun testCellMapFill() {
        assertDoesNotThrow {
            val fileName = "./src/main/resources/cells.csv"
            val cellMap = HashMap<String, Cell>()
            CSVReader(fileName, cellMap)
            assertTrue(cellMap.isNotEmpty(), "cellMap is empty")
        }
    }

    /**
     * Determines that the line parser is ignoring commas
     * that are inside internal quotes.
     */
    @Test
    fun testParser() {
        assertDoesNotThrow {
            val line = mutableListOf<String>()
            line.add("this is a string without commas")
            line.add("""this string, however, has commas inside of internal quotes""")
            parse(line.toString())
            assertTrue(line.size == 2, "Result list is not the expected size")
        }
    }

    /**
     * Tests for correct conversions and output of MakeCellObject.
     * Calls a test csv file with one, non-heading row.
     */
    @Test
    fun testLongCell() {
        val fileName = "./src/main/resources/cellsTest.csv"
        val cellMap = HashMap<String, Cell>()
        CSVReader(fileName, cellMap)
        for (cell in cellMap) {
            assertTrue(cell.value.oem == "Huawei")
            assertTrue(cell.value.model == "P40")
            assertTrue(cell.value.launchYear == 2020)
            assertTrue(cell.value.releaseStatus == "Released 2020")
            assertTrue(cell.value.dims == "148.9 x 71.1 x 8.5 mm (5.86 x 2.80 x 0.33 in)")
            assertTrue(cell.value.weight == 175F)
            assertTrue(cell.value.sim == "Single SIM (Nano-SIM/eSIM) or Hybrid Dual SIM (Nano-SIM, dual stand-by)")
            assertTrue(cell.value.displayType == "OLED capacitive touchscreen, 16M colors")
            assertTrue(cell.value.displaySize == 6.1F)
            assertTrue(cell.value.resolution == "1080 x 2340")
            assertTrue(cell.value.sensors == "Infrared face recognition, fingerprint (under display, optical), accelerometer, gyro, proximity, compass, color spectrum")
            assertTrue(cell.value.os == "Android 10")
        }
    }

    /**
     * Tests for correct conversions and output of MakeCellObject.
     * Calls a test csv file with one, non-heading row.
     */
    @Test
    fun testShortCell() {
        val fileName = "./src/main/resources/cellsTestShort.csv"
        val cellMap = HashMap<String, Cell>()
        CSVReader(fileName, cellMap)
        for (cell in cellMap){
            assertTrue(cell.value.oem == "Haier")
            assertTrue(cell.value.model == "V280")
            assertTrue(cell.value.launchYear == 2005)
            assertTrue(cell.value.releaseStatus == "Discontinued")
            assertTrue(cell.value.dims == "109 x 44 x 14.5 mm (4.29 x 1.73 x 0.57 in)")
            assertTrue(cell.value.weight == 80F)
            assertTrue(cell.value.sim == "Mini-SIM")
            assertTrue(cell.value.displayType == "65K colors")
            assertTrue(cell.value.displaySize == null)
            assertTrue(cell.value.resolution == "128 x 160")
            assertTrue(cell.value.sensors == "V1")
            assertTrue(cell.value.os == null)
        }
    }
}

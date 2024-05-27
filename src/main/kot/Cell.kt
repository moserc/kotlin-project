package kot

/**
 * Cell data class holding attributes for cell phones:
 * manufacturer, model, launch year, launch status, dims, weight, sim card,
 * display specs, features, OS.
 * @author Cheryl Moser
 */
class Cell(
    //class attributes
    var oem: String? = null,
    var model: String? = "Unknown",
    var launchYear: Int? = null,
    var releaseStatus: String? = null,
    var dims: String? = null,
    var weight: Float? = null,
    var sim: String? = null,
    var displayType: String? = null,
    var displaySize: Float? = null,
    var resolution: String? = null,
    var sensors: String? = null,
    var os: String? = null
) {
    //getters & setters are implicit

    /**
     * Custom display output for cell specifications.
     */
    override fun toString(): String {
        return "$oem, $model, $launchYear, $releaseStatus, $dims, $weight, $sim, " +
                "$displayType, $displaySize, $resolution, $sensors, $os"
    }
}

package kot

/**
 * Cell data class holding attributes for cell phones:
 * manufacturer, model, launch year, launch status, dims, weight, sim card,
 * display specs, features, OS.
 */
class Cell(
    //class attributes
    var oem: String? = null,
    var model: String? = "Unknown",
    var launch_announced: Int? = null,
    var launch_status: String? = null,
    var body_dimensions: String? = null,
    var body_weight: Float? = null,
    var body_sim: String? = null,
    var display_type: String? = null,
    var display_size: Float? = null,
    var display_resolution: String? = null,
    var features_sensors: String? = null, //todo
    var platform_os: String? = null //todo
)

{
    /**
     * Custom display output for cell specifications.
     */
    override fun toString(): String {
        return "$oem, $model, $launch_announced, $launch_status, $body_dimensions, "+
                "$body_weight, $body_sim, $display_type, $display_size, $display_resolution, " +
                "$features_sensors, $platform_os"
    }
}

//getter & setters are implicit
/*
create 7 functions:
What phone has the largest screen size?
Smallest?
Average?
Average weight?
Heaviest?
Lightest?
Average age?
Oldest?
Newest?
 */


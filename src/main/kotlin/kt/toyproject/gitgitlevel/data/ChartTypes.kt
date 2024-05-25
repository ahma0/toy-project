package kt.toyproject.gitgitlevel.data

import java.util.*

enum class ChartTypes {
    BAR, PIE, CANDLE_STICK, BAR_3D;

    companion object {
        fun of(chartName: String): ChartTypes = valueOf(chartName.uppercase())
    }
}
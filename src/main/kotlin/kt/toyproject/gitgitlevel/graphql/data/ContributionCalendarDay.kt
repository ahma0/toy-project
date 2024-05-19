package kt.toyproject.gitgitlevel.graphql.data

import java.util.*

data class ContributionCalendarDay(
    val contributionCount: Int,
    val date: Date,
    val weekday: Int,
    val contributionLevel: ContributionLevel,
) {
    enum class ContributionLevel {
        FIRST_QUARTILE,
        FOURTH_QUARTILE,
        NONE,
        SECOND_QUARTILE,
        THIRD_QUARTILE
    }
}
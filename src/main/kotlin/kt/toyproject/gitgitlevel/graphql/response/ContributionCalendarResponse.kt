package kt.toyproject.gitgitlevel.graphql.response

import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import java.util.*

class ContributionCalendarResponse(val data: Data) {
    class Data(val user: User) {
        class User(val contributionsCollection: ContributionsCollection) {
            class ContributionsCollection(val contributionCalendar: ContributionCalendar) {
                class ContributionCalendar(
                    val totalContributions: Int,
                    val weeks: List<ContributionCalendarWeek>,
                ) {
                    class ContributionCalendarWeek(
                        val firstDay: Date,
                        val contributionDays: List<ContributionCalendarDay>,
                    )
                }
            }
        }
    }
}
package kt.toyproject.gitgitlevel.graphql.response

class ContributionCountByYearResponse(val data: Data) {
    class Data(val user: User) {
        class User(val contributionsCollection: ContributionsCollection) {
            class ContributionsCollection(
                val contributionCalendar: ContributionCalendar,
            ) {
                class ContributionCalendar(
                    val totalContributions: Int,
                )
            }
        }

    }
}
package kt.toyproject.gitgitlevel.graphql


class CommitYearResponse(val data: Data) {
    class Data(val user: User) {
        class User(val contributionsCollection: ContributionsCollection) {
            class ContributionsCollection(
                val contributionYears: List<Int>,
            )
        }
    }
}

class CommitCountByYearResponse(val data: Data) {
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
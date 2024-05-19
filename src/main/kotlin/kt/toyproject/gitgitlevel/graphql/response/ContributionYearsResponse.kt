package kt.toyproject.gitgitlevel.graphql.response

class ContributionYearsResponse(val data: Data) {
    class Data(val user: User) {
        class User(val contributionsCollection: ContributionsCollection) {
            class ContributionsCollection(
                val contributionYears: List<Int>,
            )
        }
    }
}
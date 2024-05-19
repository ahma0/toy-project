package kt.toyproject.gitgitlevel.service

import kt.toyproject.gitgitlevel.graphql.ContributionCalendarDay
import kt.toyproject.gitgitlevel.graphql.GithubApi
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import org.springframework.stereotype.Service

@Service
class ChartService(
    private val githubApi: GithubApi
) {

    fun getChartImage(githubId: String): MutableMap<Int, List<ContributionCalendarDay>> {
        val contributionYears = githubApi.getContributionYearsByUser(githubId)

        return githubApi.getContributionLevel(githubId, contributionYears)

//        return githubApi.getTotalContributionCount(githubId, contributionYears)
    }

}
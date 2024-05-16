package kt.toyproject.gitgitlevel.service

import kt.toyproject.gitgitlevel.graphql.CommitYearResponse
import kt.toyproject.gitgitlevel.graphql.GithubApi
import org.springframework.stereotype.Service

@Service
class ChartService(
    private val githubApi: GithubApi
) {

    fun getChartImage(githubId: String): List<Int> {
        return githubApi.getCommittedYears(githubId)
    }

}
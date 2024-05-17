package kt.toyproject.gitgitlevel.service

import kt.toyproject.gitgitlevel.graphql.CommitYearResponse
import kt.toyproject.gitgitlevel.graphql.GithubApi
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class ChartService(
    private val githubApi: GithubApi
) {

    fun getChartImage(githubId: String): MutableMap<Int, Int> {
        val committedYears = githubApi.getCommittedYears(githubId)
        return githubApi.getCommitCount(githubId, committedYears)
    }

}
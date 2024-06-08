package kt.toyproject.gitgitlevel.graphql

import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import kt.toyproject.gitgitlevel.graphql.response.ContributionCalendarResponse
import kt.toyproject.gitgitlevel.graphql.response.ContributionCountByYearResponse
import kt.toyproject.gitgitlevel.graphql.response.ContributionYearsResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.nio.charset.Charset
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Component
class GithubApi(
    @Value("\${github.token}") private val token: String
) {

    private val client = RestClient.create("https://api.github.com/graphql")
    private val executors = Executors.newFixedThreadPool(50)

    fun getContributionYearsByUser(userName: String): List<Int> {
        return client.post()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(mapOf("query" to contributionYearsByUserQuery.replace(USER_NAME, userName)))
            .exchange { _, response ->
                assertIsSuccess(response)
                response.bodyTo(ContributionYearsResponse::class.java)!!
                    .data
                    .user
                    .contributionsCollection
                    .contributionYears
            }
    }

    /**
     * year별로 개수 생성
     */
    fun getTotalContributionCount(userName: String, years: List<Int>): MutableMap<Int, Int> {
        val completableFutures = mutableListOf<CompletableFuture<Int>>()
        years.forEach { year ->
            val completableFuture = CompletableFuture.supplyAsync({
                client.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                    .body(
                        mapOf(
                            "query" to contributionCountByUserAndYearQuery
                                .replaceFirst(USER_NAME, userName)
                                .replace(YEAR, year.toString())
                        )
                    )
                    .exchange { _, response ->
                        assertIsSuccess(response)

                        response.bodyTo(ContributionCountByYearResponse::class.java)!!
                            .data
                            .user
                            .contributionsCollection
                            .contributionCalendar
                            .totalContributions
                    }
            }, executors)

            completableFutures.add(completableFuture)
        }

        val ans = mutableMapOf<Int, Int>()
        years.withIndex().forEach {
            val index = it.index
            val year = it.value
            val completableFuture = completableFutures[index]
            ans[year] = completableFuture.get()
        }
        return ans
    }

    fun getContributionLevel(userName: String, years: List<Int>): MutableMap<Int, List<ContributionCalendarDay>> {

        val completableFutures = mutableListOf<CompletableFuture<ContributionCalendarResponse>>()
        years.forEach { year ->
            val completableFuture = CompletableFuture.supplyAsync({
                client.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                    .body(
                        mapOf(
                            "query" to contributionLevel
                                .replaceFirst(USER_NAME, userName)
                                .replace(YEAR, "2023")
                        )
                    )
                    .exchange { _, response ->
                        assertIsSuccess(response)

                        response.bodyTo(ContributionCalendarResponse::class.java)!!
                    }

            }, executors)

            completableFutures.add(completableFuture)

        }

        val ans = mutableMapOf<Int, List<ContributionCalendarDay>>()
        years.withIndex().forEach { (index, year) ->
            val completableFuture = completableFutures[index]
            val weeks = completableFuture.get().data.user.contributionsCollection.contributionCalendar.weeks

            val contributionCalendarDays = mutableListOf<ContributionCalendarDay>()
            weeks.forEach{ week ->
                week.contributionDays.forEach { day ->
                    val contributionCalendarDay = ContributionCalendarDay(
                        contributionCount = day.contributionCount,
                        date = day.date,
                        weekday = day.weekday,
                        contributionLevel = day.contributionLevel
                    )
                    contributionCalendarDays.add(contributionCalendarDay)
                }
            }

            ans[year] = contributionCalendarDays
        }

        return ans
    }

    private fun assertIsSuccess(response: RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse) {
        require(response.statusCode.is2xxSuccessful) {
            "Bad request cause status : \"${response.statusText}\" message : \"${
                response.bodyTo(
                    String::class.java
                )
            }\""
        }
    }

    companion object {
        private const val USER_NAME = "*{user_name}"
        private const val YEAR = "*{year}"

        private val contributionYearsByUserQuery: String =
            ClassPathResource("github-graphql/contribution-year-by-user.graphql").getContentAsString(Charset.defaultCharset())

        private val contributionCountByUserAndYearQuery: String =
            ClassPathResource("github-graphql/total-contribution-count-by-user-year.graphql").getContentAsString(Charset.defaultCharset())

        private val contributionLevel: String =
            ClassPathResource("github-graphql/contribution-level.graphql").getContentAsString(Charset.defaultCharset())
    }

}
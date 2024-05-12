package kt.toyproject.gitgitlevel.graphql

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.nio.charset.Charset
import java.util.concurrent.Executors

@Component
class GithubApi(
    @Value("\${github.token}") private val token: String
) {

    private val client = RestClient.create("https://api.github.com/graphql")
    private val executors = Executors.newFixedThreadPool(50)

    fun getCommittedYears(username: String): List<Int> {
        return client.post()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(mapOf("query" to commitYear.replace(USER_NAME, username)))
            .exchange { _, response ->
                assertIsSuccess(response)
                response.bodyTo(CommitYearResponse::class.java)!!
                    .data
                    .user
                    .contributionsCollection
                    .contributionYears
            }
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

        private val commitCountQuery: String =
            ClassPathResource("github-graphql/commit-count.graphql").getContentAsString(Charset.defaultCharset())

        private val commitYear: String =
            ClassPathResource("github-graphql/commit-year.graphql").getContentAsString(Charset.defaultCharset())
    }

}
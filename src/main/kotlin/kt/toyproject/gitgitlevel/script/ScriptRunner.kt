package kt.toyproject.gitgitlevel.script

import com.google.gson.Gson
import kt.toyproject.gitgitlevel.data.ChartTypes
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader

@Component
class ScriptRunner {
    fun runScript(
        contributionCountByYear:  MutableMap<Int, Int>,
        chartTypes: ChartTypes
    ): String {
        val toJson = Gson().toJson(contributionCountByYear)

        val command = "npx ts-node ./src/main/resources/scripts/index.ts '$toJson', '$chartTypes'"
        val process = ProcessBuilder(*command.split(" ").toTypedArray()).start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        return reader.toString()
    }

}
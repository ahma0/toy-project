package kt.toyproject.gitgitlevel.service

import kt.toyproject.gitgitlevel.graphql.GithubApi
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import org.springframework.stereotype.Service
import java.io.File

@Service
class ChartService(
    private val githubApi: GithubApi
) {

    fun getChartImage(githubId: String): MutableMap<Int, List<ContributionCalendarDay>> {
        val contributionYears = githubApi.getContributionYearsByUser(githubId)

        return githubApi.getContributionLevel(githubId, contributionYears)

//        return githubApi.getTotalContributionCount(githubId, contributionYears)
    }

    fun generateChartImage(): File {
        val dataset = DefaultCategoryDataset().apply {
            addValue(1.0, "Series1", "Category1")
            addValue(4.0, "Series1", "Category2")
            addValue(3.0, "Series1", "Category3")
        }

        // 차트 생성
        val chart: JFreeChart = ChartFactory.createBarChart(
            "Sample Bar Chart",  // 차트 제목
            "Category",          // X축 라벨
            "Value",             // Y축 라벨
            dataset
        )

        // 차트를 이미지 파일로 저장
        val chartFile = File("barchart.png")
        ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600)
        return chartFile
    }

}
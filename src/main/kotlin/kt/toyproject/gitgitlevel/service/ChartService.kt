package kt.toyproject.gitgitlevel.service

import kt.toyproject.gitgitlevel.data.ChartTypes
import kt.toyproject.gitgitlevel.graphql.GithubApi
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import kt.toyproject.gitgitlevel.script.ScriptRunner
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.data.category.DefaultCategoryDataset
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

@Service
class ChartService(
    private val githubApi: GithubApi,
    private val scriptRunner: ScriptRunner
) {

    fun getChartImageGroupByYear(githubId: String, types: ChartTypes): ByteArray {
        val contributionYears = githubApi.getContributionYearsByUser(githubId)
        val totalContributionCount = githubApi.getTotalContributionCount(githubId, contributionYears)

        return scriptRunner.runScript(totalContributionCount, types)
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

        // 색상 변경
        val plot: CategoryPlot = chart.categoryPlot
        val renderer: BarRenderer = plot.renderer as BarRenderer
        renderer.setSeriesPaint(0, Color.BLUE)
        renderer.setSeriesPaint(1, Color.GREEN)
        renderer.setSeriesPaint(2, Color.RED)

        // 차트를 이미지 파일로 저장
        val chartFile = File("barchart.png")
        ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600)
        return chartFile
    }

}
package kt.toyproject.gitgitlevel.controller

import kt.toyproject.gitgitlevel.data.ChartTypes
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import kt.toyproject.gitgitlevel.service.ChartService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.image.BufferedImage
import java.io.FileInputStream

@RestController
@RequestMapping("/chart")
class ChartController(
    private val chartService: ChartService
) {

    @GetMapping
    fun getChartImage(
        @RequestParam(name = "username") githubId: String,
        @RequestParam(name = "chart", defaultValue = "bar") chartType: String
    ) : ResponseEntity<ByteArray> = ResponseEntity.ok()
        .contentType(MediaType.IMAGE_PNG)
        .body(chartService.getChartImageGroupByYear(githubId, ChartTypes.of(chartType)))

    @GetMapping("/barchart")
    fun generateChartImage(): ResponseEntity<InputStreamResource> {
        val chartFile = chartService.generateChartImage()
        val resource = InputStreamResource(FileInputStream(chartFile))

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"barchart.png\"")
            .contentType(MediaType.IMAGE_PNG)
            .body(resource)
    }

}
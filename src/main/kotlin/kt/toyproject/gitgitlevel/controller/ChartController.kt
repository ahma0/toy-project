package kt.toyproject.gitgitlevel.controller

import kt.toyproject.gitgitlevel.service.ChartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/chart")
class ChartController(
    private val chartService: ChartService
) {

    @GetMapping
    fun getChartImage(@RequestParam(name = "username") githubId: String) : MutableMap<Int, Int> =
        chartService.getChartImage(githubId)

}
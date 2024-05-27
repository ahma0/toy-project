package kt.toyproject.gitgitlevel.script

import com.google.gson.Gson
import io.micrometer.core.instrument.util.StringEscapeUtils
import kt.toyproject.gitgitlevel.data.ChartTypes
import kt.toyproject.gitgitlevel.graphql.data.ContributionCalendarDay
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO

@Component
class ScriptRunner {
    fun runScript(
        contributionCountByYear: MutableMap<Int, Int>,
        chartTypes: ChartTypes
    ): ByteArray {
        val toJson = Gson().toJson(contributionCountByYear)
        val escapedJson = StringEscapeUtils.escapeJson(toJson)
        val command = "npx ts-node src/main/resources/scripts/index.ts $escapedJson, $chartTypes"
        val process = Runtime.getRuntime().exec(command)

        // 프로세스의 출력 스트림을 읽어오기 위한 BufferedReader 생성
        val reader = process.inputStream.bufferedReader()

        val output = StringBuilder()

        // BufferedReader를 통해 프로세스의 출력을 읽어옴
        val lines = reader.readLines()
        for (line in lines) {
            output.append(line)
        }

        // 프로세스가 종료될 때까지 대기
        process.waitFor()


        // TypeScript 스크립트로부터 반환된 base64 이미지 데이터를 디코딩하여 이미지로 변환
//        val imageBytes = Base64.getDecoder().decode(output.toString())
        val base64ImageSafe = output.toString().replace('-', '+').replace('_', '/')
        val imageBytes = Base64.getDecoder().decode(base64ImageSafe)
        val imageStream = ByteArrayInputStream(imageBytes)
        val bufferedImage = ImageIO.read(imageStream)

        val outputStream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", outputStream)

        return outputStream.toByteArray()
    }

}
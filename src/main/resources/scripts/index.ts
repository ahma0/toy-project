import {createBarChart} from './charts/barChart';
import {ChartTypes, ContributionData} from './data/ContributionDatas';
import {createPieChart} from "./charts/pieChart";

function makeChart(contributionDataJson: string, chartTypes: ChartTypes): Promise<string> {
    let result: Promise<string> = Promise.resolve("initial value");
    switch (chartTypes) {
        case ChartTypes.BAR:
            const contributionData: ContributionData = JSON.parse(contributionDataJson);
            result = createBarChart(contributionData);
            break;
        case ChartTypes.PIE:
            // JSON 파싱
            // const jsonData = JSON.parse(contributionDataJson);
            // result = createPieChart(jsonData);
            break;
    }
    return result
}

// main 함수 정의
if (process.argv.length < 4) {
    console.error("Usage: npx ts-node src/main/resources/scripts/index.ts <contributionDataJson> <chartType>");
    process.exit(1);
}

// Kotlin에서 전달된 데이터
const contributionDataJson: string = process.argv[2];
const chartTypes: ChartTypes = process.argv[3] as ChartTypes;

// makeChart 함수 호출
const result: Promise<string> = makeChart(contributionDataJson, chartTypes);
console.log(result);
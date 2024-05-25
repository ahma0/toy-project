import {createBarChart} from './charts/barChart';

export function makeChart(contributionDataJson: string, chartTypes: ChartTypes): string {
    const contributionData: ContributionData = JSON.parse(contributionDataJson);
    return typeof contributionData
}


interface ContributionData {
    [year: number]: number;
}

enum ChartTypes {
    BAR, PIE, CANDLE_STICK, BAR_3D
}
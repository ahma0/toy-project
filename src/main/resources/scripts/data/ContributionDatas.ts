export interface ContributionData {
    [year: string]: number;
}

export enum ChartTypes {
    BAR="BAR",
    PIE = "PIE",
    CANDLE_STICK = "CANDLE_STICK",
    BAR_3D = "BAR_3D"
}
import {ContributionData} from "../data/ContributionDatas";
import {createCanvas} from 'canvas';


export async function createBarChart(data: ContributionData): Promise<string> {
    const width = 800;
    const height = 600;
    const canvas = createCanvas(width, height);
    const ctx = canvas.getContext('2d');

    // 배경 색 설정
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, width, height);

    // 막대 차트 그리기
    const barWidth = 40;
    const barGap = 10;
    const maxDataValue = Math.max(...Object.values(data));
    const scaleFactor = height / maxDataValue;

    ctx.fillStyle = 'steelblue';

    Object.entries(data).forEach(([year, value], index) => {
        const barHeight = value * scaleFactor;
        const x = index * (barWidth + barGap);
        const y = height - barHeight;

        ctx.fillRect(x, y, barWidth, barHeight);

        // 텍스트 추가 (년도)
        ctx.fillStyle = 'black';
        ctx.font = '16px Arial';
        ctx.fillText(year, x, height - 5);
    });

    // 이미지 데이터를 base64 형식으로 반환
    return canvas.toDataURL();
}
import {ContributionData} from "../data/ContributionDatas";
import * as d3 from 'd3';

export async function createPieChart(data: ContributionData): Promise<string> {
// SVG 요소의 너비와 높이 설정
    const width = 800;
    const height = 600;

    // SVG 요소 생성
    const svg = d3.select('body').append('svg')
        .attr('width', width)
        .attr('height', height)
        .style('background-color', 'white'); // 배경색을 흰색으로 설정합니다.

    // 3D 막대 차트 생성
    const bars = svg.selectAll('rect')
        .data(Object.values(data))
        .enter()
        .append('rect')
        .attr('x', (d, i) => i * 50)
        .attr('y', d => height - d)
        .attr('width', 40)
        .attr('height', d => d)
        .attr('fill', 'steelblue'); // 막대의 색상을 steelblue로 설정합니다.

    // SVG를 이미지로 변환하여 base64로 반환
    const svgString = new XMLSerializer().serializeToString(svg.node());
    const image = new Image();
    image.src = 'data:image/svg+xml;base64,' + btoa(svgString);

    // 이미지가 로드될 때까지 기다린 후 base64 문자열 반환
    return new Promise((resolve, reject) => {
        image.onload = () => {
            const canvas = document.createElement('canvas');
            canvas.width = width;
            canvas.height = height;
            const ctx = canvas.getContext('2d');
            ctx.drawImage(image, 0, 0, width, height);
            const imageData = canvas.toDataURL('image/png');
            resolve(imageData);
        };
        image.onerror = (error) => {
            reject(error);
        };
    });
}

// 랜덤 색상을 반환하는 함수
function getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
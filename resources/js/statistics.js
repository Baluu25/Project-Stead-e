import { Chart, LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler } from 'chart.js';

Chart.register(LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler);

const canvas = document.getElementById('daily-completions-chart');
const rawData = JSON.parse(canvas.dataset.completions || '{}');

const labels = [];
const data = [];
for (let i = 6; i >= 0; i--) { 
    const d = new Date();
    d.setDate(d.getDate() - i);
    const key = d.toISOString().split('T')[0];
    labels.push(d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' }));
    data.push(rawData[key] ?? 0);
}

new Chart(canvas.getContext('2d'), {
    type: 'line',
    data: {
        labels,
        datasets: [{
            label: 'Completed habits',
            data,
            borderColor: '#2B51FE',
            backgroundColor: 'rgba(43, 81, 254, 0.1)',
            pointBackgroundColor: 'rgb(255, 255, 255)',
            pointRadius: 6,
            pointHoverRadius: 10,
            fill: true,
            tension: 0.5,
        }],
    },
    options: {
        responsive: true,
        scales: {
            y: {
                ticks: { color: 'rgb(255, 255, 255)' },
                grid: { color: 'rgba(255, 255, 255, 0.5)', lineWidth: 1 },
                beginAtZero: true,
            },
            x: {
                ticks: { color: 'rgb(255, 255, 255)' },
                grid: { color: 'rgba(255, 255, 255, 0.5)', lineWidth: 1 },
            },
        },
    },
});
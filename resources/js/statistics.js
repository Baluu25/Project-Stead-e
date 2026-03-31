import { Chart, LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler} from 'chart.js';

Chart.register(LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler);

const chart = document.getElementById('daily-completions-chart').getContext('2d');

new Chart(chart, {
    type: 'line',
    data: {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
        datasets: [{
            label: 'Completed habits',
            data: [2, 3, 1, 5, 2, 3, 3],
            borderColor: '#2B51FE',
            backgroundColor: 'rgba(43, 81, 254, 0.1)',
            pointBackgroundColor: 'rgb(255, 255, 255)',
            pointRadius: 6,
            pointHoverRadius: 10,
            fill: true,
            tension: 0.5
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                ticks: { color: 'rgb(255, 255, 255)' },
                grid: { color: 'rgba(255, 255, 255, 0.5)', lineWidth: 1, drawBorder: true },
                beginAtZero: true
            },
            x: {
                ticks: { color: 'rgb(255, 255, 255)' },
                grid: { color: 'rgba(255, 255, 255, 0.5)', lineWidth: 1, drawBorder: true }
            }
        }
    }
});
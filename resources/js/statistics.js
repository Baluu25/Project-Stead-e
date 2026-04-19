import { Chart, LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler, DoughnutController, ArcElement, Legend } from 'chart.js';
Chart.register(LineController, LineElement, PointElement, LinearScale, CategoryScale, Tooltip, Filler, DoughnutController, ArcElement, Legend);

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


// daily completions chart
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
        plugins: {
            legend: {
                position: 'bottom',
                labels: {
                    color: 'white',
                    padding: 20,
                    font: { size: 13 },
                },
            },
            tooltip: {
                callbacks: {
                    label: (item) => ` ${item.label}: ${item.raw} completions`,
                },
            },
        },
    },
});


//completions by category chart
const doughnutCanvas = document.getElementById('category-doughnut-chart');
if (doughnutCanvas) {
    const categoryData = JSON.parse(doughnutCanvas.dataset.categories || '{}');
    const categoryLabels = Object.keys(categoryData);
    const categoryValues = Object.values(categoryData);

    new Chart(doughnutCanvas.getContext('2d'), {
        type: 'doughnut',
        data: {
            labels: categoryLabels,
            datasets: [{
                data: categoryValues,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(43, 81, 254, 0.2)',
                    'rgba(255, 205, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                ],
                borderColor: [
                    'rgb(255, 99, 132)',
                    'rgb(43, 81, 254)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)',
                ],
                borderWidth: 2,
                spacing: 1.5,
                hoverOffset: 8,
            }],
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        color: 'white',
                        padding: 20,
                        font: { size: 13 },
                    },
                },
                tooltip: {
                    callbacks: {
                        label: (item) => ` ${item.label}: ${item.raw} completions`,
                    },
                },
            },
        },
    });
}
// status-charts.js

document.addEventListener('DOMContentLoaded', function() {
    // Verificar se os dados estão disponíveis
    if (!window.chartData) {
        console.error('Dados dos gráficos não encontrados');
        return;
    }

    try {
        // Inicializar gráficos com os dados globais
        initMensalChart(window.chartData.dadosMensais);
        initGeneroChart(window.chartData.generos.labels, window.chartData.generos.values);
        initEstadoChart(window.chartData.estados.labels, window.chartData.estados.values);
    } catch (error) {
        console.error('Erro ao inicializar gráficos:', error);
    }
});

function initMensalChart(dadosMensais) {
    const ctxMensal = document.getElementById('graficoMensal');
    if (!ctxMensal) return;
    
    new Chart(ctxMensal.getContext('2d'), {
        type: 'line',
        data: {
            labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
            datasets: [{
                label: 'Jogos Registrados',
                data: dadosMensais || Array(12).fill(0),
                borderColor: '#FF7657',
                backgroundColor: 'rgba(255, 118, 87, 0.1)',
                tension: 0.3,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            }
        }
    });
}

function initGeneroChart(labels, values) {
    const ctxGenero = document.getElementById('graficoGenero');
    if (!ctxGenero) return;
    
    new Chart(ctxGenero.getContext('2d'), {
        type: 'doughnut',
        data: {
            labels: labels || [],
            datasets: [{
                data: values || [],
                backgroundColor: [
                    '#FFBA5A', '#665C84', '#FF7657', '#8A7FB0', '#FF4D4D', 
                    '#FFA500', '#4CAF50', '#2196F3', '#9C27B0', '#607D8B'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'right',
                }
            }
        }
    });
}

function initEstadoChart(labels, values) {
    const ctxEstado = document.getElementById('graficoEstado');
    if (!ctxEstado) return;
    
    new Chart(ctxEstado.getContext('2d'), {
        type: 'bar',
        data: {
            labels: labels || [],
            datasets: [{
                label: 'Quantidade',
                data: values || [],
                backgroundColor: [
                    'rgba(255, 117, 87, 0.7)',
                    'rgba(101, 92, 132, 0.7)',
                    'rgba(255, 186, 90, 0.7)'
                ],
                borderColor: [
                    'rgba(255, 117, 87, 1)',
                    'rgba(101, 92, 132, 1)',
                    'rgba(255, 186, 90, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
// adm-charts.js

document.addEventListener('DOMContentLoaded', function() {
    // Ajuste para variáveis globais separadas, não objeto adminChartData
    if (typeof window.usuariosPorMes === 'undefined' || typeof window.totalAdmins === 'undefined' || typeof window.totalNaoAdmins === 'undefined') {
        console.error('Dados dos gráficos administrativos não encontrados');
        return;
    }

    try {
        initUsuariosPorMesChart(window.usuariosPorMes);
        initDistribuicaoAdminChart(window.totalAdmins, window.totalNaoAdmins);
    } catch (error) {
        console.error('Erro ao inicializar gráficos administrativos:', error);
    }
});

function initUsuariosPorMesChart(usuariosPorMes) {
    const ctxMes = document.getElementById('usuariosPorMes');
    if (!ctxMes || !usuariosPorMes) return;
    const meses = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
    const hoje = new Date();
    const mesAtual = hoje.getMonth(); // 0-based
    let labels = [], data = [];
    // Calcula o range: do mês seguinte ao atual do ano passado até o mês atual deste ano
    // Exemplo: se mesAtual = 4 (maio), pega de junho (5) até maio (4) do ano seguinte
    let range = [];
    for (let i = 1; i <= 12; i++) {
        let idx = (mesAtual + i) % 12;
        range.push(idx);
    }
    if (Array.isArray(usuariosPorMes) && Array.isArray(usuariosPorMes[0])) {
        // Array de arrays: [['Jan', 10], ...]
        labels = range.map(idx => meses[idx]);
        data = range.map(idx => usuariosPorMes[idx] ? usuariosPorMes[idx][1] : 0);
    } else if (Array.isArray(usuariosPorMes)) {
        // Array simples: [10, 20, ...]
        labels = range.map(idx => meses[idx]);
        data = range.map(idx => usuariosPorMes[idx] || 0);
    } else if (typeof usuariosPorMes === 'object' && usuariosPorMes !== null) {
        // Objeto: {1: 0, 2: 0, ..., 12: 0}
        labels = range.map(idx => meses[idx]);
        data = range.map(idx => usuariosPorMes[idx + 1] || 0);
    } else {
        console.error('Formato de usuariosPorMes não suportado:', usuariosPorMes);
        return;
    }
    new Chart(ctxMes.getContext('2d'), {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Usuários',
                data: data,
                borderColor: '#FF7657',
                backgroundColor: 'rgba(255, 118, 87, 0.1)',
                tension: 0.3,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { position: 'top' } }
        }
    });
}

function initDistribuicaoAdminChart(totalAdmins, totalNaoAdmins) {
    const ctxAdmin = document.getElementById('distribuicaoAdmin');
    if (!ctxAdmin) return;
    new Chart(ctxAdmin.getContext('2d'), {
        type: 'doughnut',
        data: {
            labels: ['Admins', 'Não Admins'],
            datasets: [{
                data: [totalAdmins, totalNaoAdmins],
                backgroundColor: ['#FFBA5A', '#665C84']
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { position: 'bottom' } }
        }
    });
}

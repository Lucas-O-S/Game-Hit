document.addEventListener('DOMContentLoaded', function() {
    // Seleciona todos os inputs de tempo
    const tempoInputs = document.querySelectorAll('.tempo-input');
    const tempoJogoHidden = document.getElementById('tempoJogo');

    // Função para converter para ISO 8601
    function converterParaISO8601() {
        const horas = parseInt(document.getElementById('horas').value) || 0;
        const minutos = parseInt(document.getElementById('minutos').value) || 0;
        const segundos = parseInt(document.getElementById('segundos').value) || 0;

        // Exemplo: PT12H20M30S
        const durationString = `PT${horas}H${minutos}M${segundos}S`;
        tempoJogoHidden.value = durationString;
    }

    // Adiciona event listeners
    tempoInputs.forEach(input => {
        input.addEventListener('input', converterParaISO8601);
        input.addEventListener('change', converterParaISO8601);
    });

    // Se houver valor pré-existente no formato ISO 8601 (ex: PT2H15M)
    const match = /^PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?$/.exec(tempoJogoHidden.value);
    if (match) {
        const horas = parseInt(match[1]) || 0;
        const minutos = parseInt(match[2]) || 0;
        const segundos = parseInt(match[3]) || 0;

        document.getElementById('horas').value = horas;
        document.getElementById('minutos').value = minutos;
        document.getElementById('segundos').value = segundos;
    }
});

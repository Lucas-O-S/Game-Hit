document.addEventListener('DOMContentLoaded', function() {
    // Seleciona todos os inputs de tempo
    const tempoInputs = document.querySelectorAll('.tempo-input');
    const tempoJogoHidden = document.getElementById('tempoJogo');
    let horas = document.getElementById('horas');
    let minutos = document.getElementById('minutos');
    let segundos = document.getElementById('segundos');

    if (tempoJogoHidden && tempoJogoHidden.value != 0) {
        horas.value = parseInt(tempoJogoHidden.value/3600) || 0;
        minutos.value = parseInt((tempoJogoHidden.value % 3600) / 60) || 0;
        segundos.value = parseInt(tempoJogoHidden.value % 60) || 0;
    }

    // Função para converter para ISO 8601
    function converterParaSegundos() {
        horas.value = parseInt(document.getElementById('horas').value) || 0;
        minutos.value = parseInt(document.getElementById('minutos').value) || 0;
        segundos.value = parseInt(document.getElementById('segundos').value) || 0;

        resultado = horas.value * 3600 + minutos.value * 60 + segundos.value * 1;

        console.log(`Convertido para ISO 8601: ${resultado}`);
        tempoJogoHidden.value = resultado;
    }

    // Adiciona event listeners
    tempoInputs.forEach(input => {
        input.addEventListener('input', converterParaSegundos);
        input.addEventListener('change', converterParaSegundos);

    });

    // Se houver valor pré-existente no formato ISO 8601 (ex: PT2H15M)
});

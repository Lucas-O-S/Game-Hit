document.addEventListener('DOMContentLoaded', function() {
    // Seleciona todos os inputs de tempo
    const tempoInputs = document.querySelectorAll('.tempo-input');
    const tempoJogoHidden = document.getElementById('tempoJogo');
    
    // Função para converter para horas decimais
    function converterParaHoras() {
        const horas = parseFloat(document.getElementById('horas').value) || 0;
        const minutos = parseFloat(document.getElementById('minutos').value) || 0;
        const segundos = parseFloat(document.getElementById('segundos').value) || 0;
        
        // Converte minutos e segundos para fração de hora
        const totalHoras = horas + (minutos / 60) + (segundos / 3600);
        
        // Arredonda para 4 casas decimais e atualiza o campo hidden
        tempoJogoHidden.value = totalHoras.toFixed(4);
    }
    
    // Adiciona event listeners
    tempoInputs.forEach(input => {
        input.addEventListener('input', converterParaHoras);
        input.addEventListener('change', converterParaHoras);
    });
    
    // Se houver valor pré-existente, converte de volta para exibir
    const tempoExistente = parseFloat(tempoJogoHidden.value);
    if (!isNaN(tempoExistente) && tempoExistente > 0) {
        const horas = Math.floor(tempoExistente);
        const minutosRestantes = (tempoExistente - horas) * 60;
        const minutos = Math.floor(minutosRestantes);
        const segundos = Math.round((minutosRestantes - minutos) * 60);
        
        document.getElementById('horas').value = horas;
        document.getElementById('minutos').value = minutos;
        document.getElementById('segundos').value = segundos;
    }
});
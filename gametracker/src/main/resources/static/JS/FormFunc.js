function ErrorAlert(error) {
  Swal.fire({
    icon: 'error',
    title: 'Erro!',
    text: error,
    background: '#f8d7da',  // Cor de fundo do alerta de erro
    color: '#721c24',        // Cor do texto
    iconColor: '#721c24',    // Cor do ícone (erro)
    confirmButtonColor: '#d9534f',  // Cor do botão de confirmação (vermelho claro, compatível com o erro)
    confirmButtonText: 'OK',
    customClass: {
      popup: 'custom-alert-popup',
      title: 'custom-alert-title',
      content: 'custom-alert-content'
    }
  });
}

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM carregado!");
  const errorElement = document.getElementById("erro").value;

  if (errorElement) {
    ErrorAlert(errorElement);
  }
});

function confirmarExclusao() {
  Swal.fire({
    title: 'Tem certeza?',
    text: "Esta ação não poderá ser desfeita!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#6c757d',
    confirmButtonText: 'Sim, excluir',
    cancelButtonText: 'Cancelar',
    background: '#fff3f3',
    color: '#721c24'
  }).then((result) => {
    if (result.isConfirmed) {
      document.getElementById('formExcluir').submit();
    }
  });
}

if (window.jQuery) {
  $(document).ready(function () {
    $('#telefone').mask('(00) 00000-0000');
  });
} else {
  console.error("jQuery não carregado!");
}

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


if (window.jQuery) {
  $(document).ready(function () {
    $('#telefone').mask('(00) 00000-0000');
  });
} else {
  console.error("jQuery não carregado!");
}

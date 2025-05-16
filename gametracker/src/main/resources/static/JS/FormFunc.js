function ErrorAlert(error) {
  Swal.fire({
    icon: 'error',
    title: 'Erro!',
    text: error
  });
}

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM carregado!");
  const errorElement = document.getElementById("erro").value;

  if (errorElement) {
    if (errorElement) {  
      ErrorAlert(errorElement);
    }
  }
});

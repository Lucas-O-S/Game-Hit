function ErrorAlert(error) {
  Swal.fire({
    icon: 'error',
    title: 'Erro!',
    text: error
  });
}

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM carregado!");
  const error = document.getElementById("error").value;
  if(error != "" && error != null && error != undefined){
    ErrorAlert(error);

  }
})
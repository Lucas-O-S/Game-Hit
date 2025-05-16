document.addEventListener("DOMContentLoaded", function() {
  const logoutLink = document.getElementById("logout-link");
  if(logoutLink){
    logoutLink.addEventListener("click", function(event) {
      event.preventDefault();
      const logoutForm = document.getElementById("logout-form");
      if(logoutForm) logoutForm.submit();
    });
  }
});

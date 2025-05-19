function exibirImagem(event) {
    const input = event.target;
    const file = input.files[0];
    const preview = document.getElementById('imgPreview');
    const icon = document.getElementById('uploadIcon');

    if (file && preview) {
      const reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
        preview.style.display = 'block';
        if (icon) icon.style.display = 'none';
      };
      reader.readAsDataURL(file);
    } else {
      preview.src = '#';
      preview.style.display = 'none';
      if (icon) icon.style.display = 'inline-block';
    }
  }
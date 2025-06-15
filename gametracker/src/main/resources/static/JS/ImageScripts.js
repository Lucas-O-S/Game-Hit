function exibirImagem(event) {
    const input = event.target;
    const file = input.files[0];
    const preview = document.getElementById('imgPreview');
    const icon = document.getElementById('uploadIcon');

    // Validação do tipo de arquivo
    if (file) {
      const validTypes = ['image/jpeg', 'image/png'];
      if (!validTypes.includes(file.type)) {
        if (typeof ErrorAlert === 'function') {
          ErrorAlert('Apenas arquivos JPG ou PNG são permitidos.');
        } else {
          alert('Apenas arquivos JPG ou PNG são permitidos.');
        }
        input.value = '';
        if (preview) {
          preview.src = '#';
          preview.style.display = 'none';
        }
        if (icon) icon.style.display = 'inline-block';
        return;
      }
    }

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
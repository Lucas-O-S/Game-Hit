function confirmarExclusao(formId, mensagem) {
    Swal.fire({
        title: 'Tem certeza?',
        text: mensagem,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#FF7657',
        cancelButtonColor: '#665C84',
        confirmButtonText: 'Sim, excluir!',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById(formId).submit();
        }
    });
}
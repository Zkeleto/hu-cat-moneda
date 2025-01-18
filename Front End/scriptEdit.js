window.onload = function () {
    // Obtener la moneda seleccionada del sessionStorage
    const monedaSeleccionada = JSON.parse(sessionStorage.getItem("monedaSeleccionada"));
    if (!monedaSeleccionada) {
        alert("No se pudo encontrar la moneda a editar.");
        window.location.href = "index.html"; // Redirigir al listado si no se encuentra la moneda
    } else {
        // Rellenar el formulario con los datos de la moneda seleccionada
        document.getElementById("numCia").value = monedaSeleccionada.numCia;
        document.getElementById("claveMoneda").value = monedaSeleccionada.claveMoneda;
        document.getElementById("descripcion").value = monedaSeleccionada.descripcion;
        document.getElementById("simbolo").value = monedaSeleccionada.simbolo;
        document.getElementById("abreviacion").value = monedaSeleccionada.abreviacion;
        document.getElementById("monedaCorriente").value = monedaSeleccionada.monedaCorriente;
        document.getElementById("status").value = monedaSeleccionada.status;
    }
};

// Manejar la actualización de la moneda
function updateMoneda() {
    // Capturar datos del formulario
    const numCia = document.getElementById('numCia').value.trim();
    const claveMoneda = document.getElementById('claveMoneda').value.trim();
    const descripcion = document.getElementById('descripcion').value.trim();
    const simbolo = document.getElementById('simbolo').value.trim();
    const abreviacion = document.getElementById('abreviacion').value.trim();
    const monedaCorriente = document.getElementById('monedaCorriente').value.trim().toUpperCase();
    const status = document.getElementById('status').value.trim().toUpperCase();

    // Validar campos obligatorios
    if (!numCia || !claveMoneda || !descripcion || !simbolo || !abreviacion || !monedaCorriente || !status) {
        alert('Por favor, completa todos los campos.');
        return;
    }

    // Validar longitud y valores permitidos
    if (monedaCorriente !== 'S' && monedaCorriente !== 'N') {
        alert('El campo "Moneda Corriente" solo acepta los valores S o N.');
        return;
    }
    if (status !== 'A' && status !== 'I') {
        alert('El campo "Status" solo acepta los valores A o I.');
        return;
    }

    // Crear objeto con los datos actualizados
    const monedaActualizada = {
        numCia: parseInt(numCia, 10), // Convertir a número
        claveMoneda: claveMoneda.toUpperCase(),
        descripcion: descripcion,
        simbolo: simbolo,
        abreviacion: abreviacion.toUpperCase(),
        monedaCorriente: monedaCorriente.toUpperCase(),
        status: status.toUpperCase(),
    };

    // Enviar la actualización a la API
    fetch('http://localhost:8010/api/v1/moneda', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(monedaActualizada),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al actualizar la moneda');
            }
            return response.json();
        })
        .then(data => {
            alert('Moneda actualizada exitosamente.');
            window.location.href = "index.html"; // Volver al listado
        })
        .catch(error => {
            console.error('Error al actualizar la moneda:', error);
            alert('Ocurrió un error al actualizar la moneda.');
        });
};



// Cancelar edición
function cancelEdit() {
    if (confirm('¿Estás seguro de cancelar la edición?')) {
        window.location.href = 'index.html';
    }
}
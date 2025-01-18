window.onload = function() {
    loadMonedas(); // Llamada a la función al cargar la página
};
//listar
function loadMonedas() {
    console.log('Cargando monedas...');
    fetch('http://localhost:8010/api/v1/moneda')
        .then(response => {
            console.log('Dentro del fetch');
            if (response.status === 204) {
                console.warn('No hay datos disponibles, se recibió un 404');
                return []; // Devuelve un array vacío si no hay datos
            }
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
            return response.json(); // Procesa el JSON si la respuesta es correcta
        })
        .then(data => {
            console.log('Datos de monedas:', data);
            const tableBody = document.getElementById('moneda-table').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar nuevas filas
        
            // Asegúrate de que data es un arreglo
            if (!Array.isArray(data) || data.length === 0) {
                const row = tableBody.insertRow();
                const cell = row.insertCell();
                cell.colSpan = 8; 
                cell.textContent = 'No hay monedas disponibles. Añade una nueva moneda.';
                cell.style.textAlign = 'center';
            } else {
                data.forEach(moneda => {
                    console.log(moneda)
                    const row = tableBody.insertRow();
                    const monedaJson = encodeURIComponent(JSON.stringify(moneda));
                    row.innerHTML = `
                        <td>${moneda.numCia}</td>
                        <td>${moneda.claveMoneda}</td>
                        <td>${moneda.descripcion}</td>
                        <td>${moneda.simbolo}</td>
                        <td>${moneda.abreviacion}</td>
                        <td>${moneda.monedaCorriente}</td>
                        <td>${moneda.status}</td>
                        <td class = buttons>
                            <button class="edit-btn" onclick="redirigirEditar('${monedaJson}')">Editar</button>
                            <button class="delete-btn" onclick="deleteMoneda('${moneda.numCia}', '${moneda.claveMoneda}')">Eliminar</button>
                        </td>
                    `;
                });
            }
        })        
        .catch(error => {
            console.error('Error al cargar las monedas:', error);
        });
}
function redirigirEditar(monedaJson) {
    // Decodificar el JSON recibido
    const moneda = JSON.parse(decodeURIComponent(monedaJson));
    console.log("Redirigiendo para editar moneda:", moneda);
    
    // Almacenar la moneda en sessionStorage
    sessionStorage.setItem("monedaSeleccionada", JSON.stringify(moneda));
    
    // Redirigir a la página de edición
    window.location.href = "editar.html";
}



//registrar
document.getElementById('moneda-form').addEventListener('submit', function (event) {
    event.preventDefault(); // Evitar recargar la página

    // Capturar datos del formulario
    const numCia = document.getElementById('numCia').value.trim();
    const claveMoneda = document.getElementById('claveMoneda').value.trim();
    const descripcion = document.getElementById('descripcion').value.trim();
    const simbolo = document.getElementById('simbolo').value.trim();
    const abreviacion = document.getElementById('abreviacion').value.trim();
    const monedaCorriente = document.getElementById('monedaCorriente').value.trim().toUpperCase();
    const status = document.getElementById('status').value.trim().toUpperCase();

    // Validar que numCia sea positivo y válido
    if (!numCia || isNaN(numCia) || parseInt(numCia, 10) < 0) {
        alert('El campo "Número de agencia" no acepta numeros negativos.');
        return;
    }else if( numCia.length>4){
        alert('El campo "Número de agencia" debe ser un número maximo de 4 digitos.');
        return;
    }

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

    // Crear objeto de la moneda
    const nuevaMoneda = {
        numCia: parseInt(numCia, 10), // Convertir a número
        claveMoneda: claveMoneda.toUpperCase(),
        descripcion: descripcion,
        simbolo: simbolo,
        abreviacion: abreviacion.toUpperCase(),
        monedaCorriente: monedaCorriente.toUpperCase(),
        status: status.toUpperCase(),
    };

    // Enviar la moneda a la API
    fetch('http://localhost:8010/api/v1/moneda', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(nuevaMoneda),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al agregar la moneda');
            }
            return response.json();
        })
        .then(data => {
            alert('Moneda agregada exitosamente.');
            document.getElementById('moneda-form').reset(); // Limpiar formulario
            loadMonedas(); // Recargar la tabla
        })
        .catch(error => {
            console.error('Error al agregar la moneda:', error);
            alert('Ocurrió un error al agregar la moneda.');
        });
});
//eliminar
function deleteMoneda(numCia, claveMoneda) {
    if (confirm(`¿Estás seguro de eliminar la moneda con numCia: ${numCia}, claveMoneda: ${claveMoneda.toUpperCase()}?`)) {
        fetch(`http://localhost:8010/api/v1/moneda/${numCia}/${claveMoneda}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.status === 204) {
                alert('Moneda eliminada exitosamente');
                loadMonedas(); // Recargar la tabla después de eliminar
            } else if (response.status === 404) {
                alert('Moneda no encontrada para eliminar');
            } else {
                throw new Error('Error al eliminar la moneda');
            }
        })
        .catch(error => {
            console.error('Error al eliminar la moneda:', error);
            alert(error.message);
        });
    }
}
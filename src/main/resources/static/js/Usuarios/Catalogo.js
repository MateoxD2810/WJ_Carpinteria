document.querySelectorAll('.addToCart').forEach(button => {
    button.addEventListener('click', function() {
        const productName = this.parentElement.parentElement.querySelector('.card-title').textContent;
        const productPrice = parseFloat(this.parentElement.parentElement.querySelector('.card-text').textContent.replace('$', '').replace('.', '').replace('.', '')); // Precio como número

        // Crear un objeto que contenga la información del producto
        const product = {
            name: productName,
            price: productPrice,
            quantity: 1, // Cantidad inicial
            total: productPrice // Total inicial (igual al precio normal)
        };

        // Obtener los productos del carrito del almacenamiento local
        let cartItems = localStorage.getItem('cartItems');
        cartItems = cartItems ? JSON.parse(cartItems) : [];

        // Verificar si el producto ya está en el carrito
        const existingProductIndex = cartItems.findIndex(item => item.name === product.name);
        if (existingProductIndex !== -1) {
            // Si el producto ya está en el carrito, aumentar la cantidad y actualizar el precio total
            cartItems[existingProductIndex].quantity++;
            cartItems[existingProductIndex].total = cartItems[existingProductIndex].price * cartItems[existingProductIndex].quantity;
        } else {
            // Si el producto no está en el carrito, agregarlo al carrito
            cartItems.push(product);
        }

        // Guardar los productos en el almacenamiento local
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    });
});

function mostrarNotificacion() {
    // Mostrar la notificación
    document.getElementById('notificacion').classList.remove('hidden');
}

function cerrarNotificacion() {
    // Ocultar la notificación al hacer clic en el botón "Aceptar"
    document.getElementById('notificacion').classList.add('hidden');
}
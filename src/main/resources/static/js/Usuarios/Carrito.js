// Obtener los productos del carrito del almacenamiento local y renderizarlos en la tabla
document.addEventListener("DOMContentLoaded", function() {
    let cartItems = localStorage.getItem('cartItems');
    cartItems = cartItems ? JSON.parse(cartItems) : [];

    const cartTableBody = document.getElementById('cartItems');
    cartTableBody.innerHTML = '';

    cartItems.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.name}</td>
            <td>$${item.price.toLocaleString()}</td>
            <td>
                <button class="btn btn-secondary btn-sm decrement" data-name="${item.name}" data-price="${item.price}">-</button>
                <span class="quantity">${item.quantity}</span>
                <button class="btn btn-warning btn-sm increment" data-name="${item.name}" data-price="${item.price}">+</button>
            </td>
            <td class="total">$${(item.price * item.quantity).toLocaleString()}</td>
            <td><button class="btn btn-warning deleteCartItem"><i class="fa-solid fa-trash"></i></button></td>
        `;
        cartTableBody.appendChild(row);
    });

    // Event listener para eliminar un producto del carrito
    document.querySelectorAll('.deleteCartItem').forEach(button => {
        button.addEventListener('click', function() {
            const productName = this.parentElement.parentElement.querySelector('td').textContent;
            const updatedCartItems = cartItems.filter(item => item.name !== productName);
            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            location.reload(); // Recargar la página para reflejar los cambios
        });
    });

    // Event listener para incrementar la cantidad
    document.querySelectorAll('.increment').forEach(button => {
        button.addEventListener('click', function() {
            const productName = this.dataset.name;
            const productPrice = parseFloat(this.dataset.price);
            const updatedCartItems = cartItems.map(item => {
                if (item.name === productName) {
                    return { ...item, quantity: item.quantity + 1 };
                }
                return item;
            });
            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            location.reload(); // Recargar la página para reflejar los cambios
        });
    });

    // Event listener para decrementar la cantidad
    document.querySelectorAll('.decrement').forEach(button => {
        button.addEventListener('click', function() {
            const productName = this.dataset.name;
            const productPrice = parseFloat(this.dataset.price);
            const updatedCartItems = cartItems.map(item => {
                if (item.name === productName && item.quantity > 1) {
                    return { ...item, quantity: item.quantity - 1 };
                }
                return item;
            });
            localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
            location.reload(); // Recargar la página para reflejar los cambios
        });
    });
});
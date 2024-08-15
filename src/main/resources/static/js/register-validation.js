$(document).ready(function() {
    $('#registerForm').on('submit', function(event) {
        let isValid = true;
        $('.error-message').text('');

        const username = $('#username').val().trim();
        const fullName = $('#fullName').val().trim();
        const email = $('#email').val().trim();
        const password = $('#password').val().trim();
        const confirmPassword = $('#confirmPassword').val().trim();

        if (username.length < 3) {
            $('#usernameError').text('Username must be at least 3 characters long');
            isValid = false;
        }

        if (fullName === '') {
            $('#fullNameError').text('Full name is required');
            isValid = false;
        }

        const emailPattern = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
        if (!emailPattern.test(email)) {
            $('#emailError').text('Email should be valid');
            isValid = false;
        }

        if (password.length < 6) {
            $('#passwordError').text('Password must be at least 6 characters long');
            isValid = false;
        }

        if (password !== confirmPassword) {
            $('#confirmPasswordError').text('Passwords do not match');
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});

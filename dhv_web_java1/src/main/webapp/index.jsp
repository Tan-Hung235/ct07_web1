<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Form Validator</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <img class="hero-image" src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?auto=format&fit=crop&w=800&q=80" alt="Hero image">
        <form id="registration-form">
            <h1>Register</h1>
            <!--username-->
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" placeholder="Enter username">
                <!--thong bao-->
                <small></small>
            </div>
            <!--email-->
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" placeholder="Enter email">
                <!--thong bao-->
                <small></small>
            </div>
            <!--password-->
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" placeholder="Enter password">
                <!--thong bao-->
                <small></small>
            </div>
            <!--confirm password-->
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" placeholder="Confirm Password">
                <!--thong bao-->
                <small></small>
            </div>
            <!--button-->
            <button type="submit">Register</button>
        </form>

    </div>
    <script src="index.js"></script>
</body>
</html>
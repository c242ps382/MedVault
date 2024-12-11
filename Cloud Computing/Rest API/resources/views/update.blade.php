<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User</title>
</head>
<body>
    <h1>Update User</h1>
    <form action="{{ url('api/user/update/1') }}" method="POST" enctype="multipart/form-data">
        @csrf
        @method('PUT') <!-- Spoof HTTP PUT method -->
        <input type="text" name="name" placeholder="Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="alamat" placeholder="Alamat" required>
        <input type="file" name="imgprofile"><br>
        <button type="submit">Update</button>
    </form>
</body>
</html>

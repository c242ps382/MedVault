<!DOCTYPE html>
<html lang="ID">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Datepicker CSS -->
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
    <title>Upload Berita</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        h1 {
            text-align: center;
            color: #4CAF50;
        }

        form {
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 400px;
            width: 100%;
        }

        form input[type="text"],
        form input[type="file"],
        form button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }

        form button {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        form button:hover {
            background-color: #45a049;
        }

        form input:focus {
            outline: none;
            border-color: #4CAF50;
        }

        form input[type="file"] {
            padding: 5px;
        }
    </style>
</head>

<body>
    <div>
        <h1>Upload Berita</h1>
        <form action="{{ url('api/berita/store') }}" method="POST" enctype="multipart/form-data">
            @csrf
            @method('POST')
            <input type="text" id="update" name="update" placeholder="dd/mm/yyyy" required>
            <input type="text" name="title" placeholder="Judul Berita" required>
            <input type="file" name="imageurl" required>
            <input type="text" name="infotitle" placeholder="Judul Informasi" required>
            <textarea name="description" placeholder="Deskripsi Berita" rows="4" style="resize: both;" required></textarea>
            <button type="submit">Kirim Berita</button>
        </form>

    </div>

    <!-- jQuery -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Datepicker JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <!-- Inputmask -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/inputmask/5.0.8/jquery.inputmask.min.js"></script>

    <script>
        $(document).ready(function () {
            // Inisialisasi Inputmask
            $('input[name="update"]').inputmask('99/99/9999', { placeholder: 'dd/mm/yyyy' });

            // Inisialisasi Datepicker
            $('#update').datepicker({
                format: 'dd/mm/yyyy',
                autoclose: true,
                todayHighlight: true
            });
        });
    </script>
</body>

</html>

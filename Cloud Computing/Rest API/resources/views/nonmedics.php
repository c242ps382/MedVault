<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tambah Non Medic</title>
</head>
<body>
    <h1>Tambah Non Medic</h1>
    <form action="{{ url('api/nonmedics/store') }}" method="POST" enctype="multipart/form-data">
        <!-- Tambahkan CSRF Token -->
        <input type="hidden" name="_token" value="{{ csrf_token() }}">

        <!-- Nomor Kunjungan -->
        <input type="text" name="nomor_kunjungan" placeholder="Nomor Kunjungan" required><br>

        <!-- Tanggal Kunjungan -->
        <input type="date" name="tanggal_kunjungan" required><br>

        <!-- Nama Pasien -->
        <select name="nama_pasien" id="nama_pasien" required>
    <option value="">Pilih Nama Pasien</option>
    @foreach ($patients as $patient)
        <option value="{{ $patient->username }}">
            {{ $patient->username }} | {{ $patient->alamat }}
        </option>
    @endforeach
</select>

        <!-- Keluhan -->
        <textarea name="keluhan" placeholder="Keluhan"></textarea><br>

        <!-- Tindakan -->
        <textarea name="tindakan" placeholder="Tindakan"></textarea><br>

        <!-- Biaya Pembayaran -->
        <input type="number" name="biaya_pembayaran" placeholder="Biaya Pembayaran (IDR)" required><br>

        <!-- Submit Button -->
        <button type="submit">Simpan</button>
    </form>
</body>
</html>

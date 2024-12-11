<?php

namespace App\Http\Controllers\Api;


use App\Http\Controllers\Controller;
use App\Models\Patient;
use Illuminate\Http\Request;

class PatientController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $patients = Patient::all();

        return response()->json([
            'message' => 'Data pasien berhasil diambil.',
            'data' => $patients
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
      // Validasi data yang dikirimkan
      $validatedData = $request->validate([
        'nama' => 'required|string|max:255',
        'nik' => 'required|string|size:16',
        'tanggal_lahir' => 'required|date',
        'alamat' => 'required|string',
        'jenis_kelamin' => 'required|in:Laki-laki,Perempuan',
        'umur' => 'required|integer',  // Perbaikan disini
        'nomor_hp' => 'required|string|max:15',
    ]);

    // Simpan data pasien ke database
    $patient = Patient::create($validatedData);

    // Kembalikan response JSON
    return response()->json([
        'message' => 'Data pasien berhasil disimpan.',
        'data' => $patient,
    ], 201);
}


    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $validatedData = $request->validate([
            'nama' => 'required|string|max:255',
            'nik' => 'required|digits:16|unique:patients,nik,' . $id,
            'tanggal_lahir' => 'required|date',
            'umur' => 'required|integer|min:1',
            'alamat' => 'required|string|max:500',
            'jenis_kelamin' => 'required|in:Laki-laki,Perempuan',
            'nomor_hp' => 'required|string|max:15',
        ]);

        $patient = Patient::findOrFail($id);

        if (!$patient) {
            return response()->json(['message' => 'Patient not found'], 404);
        }

        // Perbarui data pasien
        $patient->update($validatedData);

        // Kembalikan respons JSON
        return response()->json([
            'message' => 'Data pasien berhasil diperbarui.',
            'data' => $patient,
        ], 200);
    }


    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
    // Cari pasien berdasarkan ID
    $patient = Patient::findOrFail($id);

    // Hapus data pasien
    $patient->delete();

    // Kembalikan respons JSON
    return response()->json([
        'message' => 'Data pasien berhasil dihapus.',
    ], 200);
}
}


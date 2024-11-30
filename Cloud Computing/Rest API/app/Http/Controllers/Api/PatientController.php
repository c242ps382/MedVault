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

    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
      // Validasi data yang dikirimkan
      $validatedData = $request->validate([
        'nama' => 'required|string|max:255',
        'nik' => 'required|string|size:16|unique:patients,nik',
        'tanggal_lahir' => 'required|date',
        'agama' => 'required|string|max:50',
        'alamat' => 'required|string',
        'jenis_kelamin' => 'required|in:Laki-laki,Perempuan',
        'nomor_hp' => 'required|string|max:15|unique:patients,nomor_hp',
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
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}

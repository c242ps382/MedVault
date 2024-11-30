<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Medic;
use Illuminate\Http\Request;

class MedicController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'nomor_kunjungan' => 'required|string|unique:medics,nomor_kunjungan',
            'tanggal_kunjungan' => 'required|date',
            'nama_pasien' => 'required|string|max:255',
            'anamnesa' => 'required|string',
            'hasil_diagnosa' => 'required|string',
            'tindakan' => 'required|string',
        ]);

        // Simpan data kunjungan medis ke database
        $medic = Medic::create($validatedData);

        // Kembalikan response JSON
        return response()->json([
            'message' => 'Data kunjungan medis berhasil disimpan.',
            'data' => $medic,
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

<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Patient;
use App\Models\Nonmedics;
use Illuminate\Http\Request;

class NonmedicController extends Controller
{
    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        // Validasi request
        $validatedData = $request->validate([
            'nomor_kunjungan' => 'required|unique:nonmedics',
            'tanggal_kunjungan' => 'required|date',
            'nama_pasien' => 'required|exists:patients,nama', // Pastikan username ada di tabel users
            'keluhan' => 'nullable|string',
            'tindakan' => 'nullable|string',
            'biaya_pembayaran' => 'required|numeric',
        ]);

        // Simpan data
        $nonmedic = Nonmedics::create($validatedData);
        return response()->json([
            'message' => 'Data successfully created',
            'data' => $nonmedic,
        ], 201);
    }



    public function update(Request $request, string $id)
{
    // Cari data Nonmedics berdasarkan ID
    $nonmedic = Nonmedics::findOrFail($id);

    // Validasi request parsial
    $validatedData = $request->validate([
        'nomor_kunjungan' => 'sometimes|required|string|unique:nonmedics,nomor_kunjungan,' . $id,
        'tanggal_kunjungan' => 'sometimes|required|date',
        'nama_pasien' => 'sometimes|required|exists:patients,nama',
        'keluhan' => 'sometimes|nullable|string',
        'tindakan' => 'sometimes|nullable|string',
        'biaya_pembayaran' => 'sometimes|nullable|numeric',
    ]);

    // Update data hanya sesuai request yang dikirim
    $nonmedic->update($validatedData);

    return response()->json([
        'message' => 'Data successfully updated.',
        'data' => $nonmedic,
    ]);
}
    /**
     * Get list of patients (username | address).
     */
    public function getPatientOptions()
    {
        $patients = Patient::select('username', 'alamat')
            ->get()
            ->map(function ($patient) {
                return [
                    'id' => $patient->nama, // ID yang dikirim (username)
                    'label' => "{$patient->nama} | {$patient->nama}", // Teks untuk ditampilkan
                ];
            });

        return response()->json([
            'message' => 'Patient options fetched successfully.',
            'data' => $patients,
        ]);
    }
}

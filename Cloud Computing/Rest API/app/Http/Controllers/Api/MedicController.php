<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Medic;
use App\Models\Patient;
use Illuminate\Http\Request;

class MedicController extends Controller
{
    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'nomor_kunjungan' => 'required|string|unique:medics,nomor_kunjungan',
            'tanggal_kunjungan' => 'required|date',
            'nama_pasien' => 'required|exists:patients,nama',
            'anamnesa' => 'required|array', // Validasi untuk data anamnesa sebagai array
            'anamnesa.sistole_diastol' => 'required|string|max:20', // Sistole/Diastole
            'anamnesa.suhu' => 'required|string|max:10', // Suhu
            'anamnesa.detak_nadi' => 'required|string|max:10', // Detak Nadi
            'anamnesa.berat_badan' => 'required|string|max:10', // Berat Badan
            'anamnesa.tinggi_badan' => 'required|string|max:10', // Tinggi Badan
            'gejala' => 'required|string',
            'hasil_diagnosa' => 'required|string',
            'tindakan' => 'required|string',
        ]);

        // Gabungkan data anamnesa ke satu kolom JSON
        $anamnesa = json_encode($validatedData['anamnesa']);

        // Simpan data kunjungan medis ke database
        $medic = Medic::create([
            'nomor_kunjungan' => $validatedData['nomor_kunjungan'],
            'tanggal_kunjungan' => $validatedData['tanggal_kunjungan'],
            'nama_pasien' => $validatedData['nama_pasien'],
            'anamnesa' => $anamnesa, // Simpan sebagai JSON
            'gejala' => $validatedData['gejala'],
            'hasil_diagnosa' => $validatedData['hasil_diagnosa'],
            'tindakan' => $validatedData['tindakan'],
        ]);

        // Kembalikan response JSON
        return response()->json([
            'message' => 'Data kunjungan medis berhasil disimpan.',
            'data' => $medic,
        ], 201);
    }

    /**
     * Display the specified resource.
     */

     public function update(Request $request, string $id)
     {
         // Cari data berdasarkan ID
         $medic = Medic::findOrFail($id);

         // Validasi data request secara parsial
         $validatedData = $request->validate([
             'nomor_kunjungan' => 'sometimes|required|string|unique:medics,nomor_kunjungan,' . $id,
             'tanggal_kunjungan' => 'sometimes|required|date',
             'nama_pasien' => 'sometimes|required|exists:patients,nama',
             'hasil_diagnosa' => 'sometimes|required|string',
             'tindakan' => 'sometimes|required|string',
             'gejala' => 'sometimes|required|string',
             'anamnesa' => 'sometimes|required|array',
             'anamnesa.sistole_diastol' => 'sometimes|required_with:anamnesa|string|max:20',
             'anamnesa.suhu' => 'sometimes|required_with:anamnesa|string|max:10',
             'anamnesa.detak_nadi' => 'sometimes|required_with:anamnesa|string|max:10',
             'anamnesa.berat_badan' => 'sometimes|required_with:anamnesa|string|max:10',
             'anamnesa.tinggi_badan' => 'sometimes|required_with:anamnesa|string|max:10',
         ]);

         // Jika ada data anamnesa, ubah menjadi JSON
         if (isset($validatedData['anamnesa'])) {
             $validatedData['anamnesa'] = json_encode($validatedData['anamnesa']);
         }

         // Perbarui data hanya sesuai request yang dikirim
         $medic->update($validatedData);

         // Mengembalikan response JSON
         return response()->json([
             'message' => 'Data kunjungan medis berhasil diperbarui.',
             'data' => $medic,
         ]);
     }

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

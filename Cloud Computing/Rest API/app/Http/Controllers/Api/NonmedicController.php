<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Nonmedics;
use Illuminate\Http\Request;

class NonmedicController extends Controller
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
            'nomor_kunjungan' => 'required|unique:nonmedics',
            'tanggal_kunjungan' => 'required|date',
            'nama_pasien' => 'required|string',
            'keluhan' => 'nullable|string',
            'tindakan' => 'nullable|string',
            'biaya_pembayaran' => 'required|numeric',
        ]);

        $nonmedic = Nonmedics::create($validatedData);

        return response()->json([
            'message' => 'Data successfully created',
            'data' => $nonmedic,
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

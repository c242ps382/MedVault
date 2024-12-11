<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use App\Models\Medic;
use App\Models\Nonmedics;
use Illuminate\Http\Request;

class KunjunganController extends Controller
{
    public function getAllKunjungan()
    {
        // Ambil data kunjungan medis beserta informasi pasien
        $medics = Medic::with('patient')->get()->map(function ($medic) {
            return [
                'id' => $medic->id,
                'username' => $medic->patient ? $medic->patient->nama : null,
                'alamat' => $medic->patient ? $medic->patient->alamat : null,
                'nomor_hp' => $medic->patient ? $medic->patient->nomor_hp : null,
                'tanggal_kunjungan' => $medic->tanggal_kunjungan,
                'nomor_kunjungan' => $medic->nomor_kunjungan,
                'hasil_diagnosa' => $medic->hasil_diagnosa,
                'tindakan' => $medic->tindakan,
                'type' => 'Medis',
            ];
        });

        // Ambil data non-medis beserta informasi pasien
        $nonmedics = Nonmedics::with('patient')->get()->map(function ($nonmedic) {
            return [
                'id' => $nonmedic->id,
                'username' => $nonmedic->patient ? $nonmedic->patient->nama : null,
                'alamat' => $nonmedic->patient ? $nonmedic->patient->alamat : null,
                'nomor_hp' => $nonmedic->patient ? $nonmedic->patient->nomor_hp : null,
                'tanggal_kunjungan' => $nonmedic->tanggal_kunjungan,
                'nomor_kunjungan' => $nonmedic->nomor_kunjungan,
                'keluhan' => $nonmedic->keluhan,
                'tindakan' => $nonmedic->tindakan,
                'biaya_pembayaran' => $nonmedic->biaya_pembayaran,
                'type' => 'Non-Medis',
            ];
        });

        // Gabungkan data medis dan non-medis
        $allKunjungan = $medics->merge($nonmedics);

        return response()->json([
            'message' => 'Data kunjungan berhasil dimuat',
            'data' => $allKunjungan,
        ]);
    }
    public function getKunjunganDetail($type, $id)
{
    if (strtolower($type) === 'medis') {
        $kunjungan = Medic::with('patient')->find($id);
        if ($kunjungan && $kunjungan->patient) {
            return response()->json([
                'user' => [
                    'nama' => $kunjungan->patient->nama,
                    'tanggal_lahir' => $kunjungan->patient->tanggal_lahir,
                    'alamat' => $kunjungan->patient->alamat,
                    'jenis_kelamin' => $kunjungan->patient->jenis_kelamin,
                    'umur' => $kunjungan->patient->umur
                ],
                'kunjungan' => [
                    'id' => $kunjungan->id,
                    'nomor_kunjungan' => $kunjungan->nomor_kunjungan,
                    'tanggal_kunjungan' => $kunjungan->tanggal_kunjungan,
                    'anamnesa' => $kunjungan->anamnesa,
                    "gejala" =>$kunjungan->gejala,
                    'hasil_diagnosa' => $kunjungan->hasil_diagnosa,
                    'tindakan' => $kunjungan->tindakan
                ]
            ]);
        }
    } elseif (strtolower($type) === 'non-medis') {
        $kunjungan = Nonmedics::with('patient')->find($id);
        if ($kunjungan && $kunjungan->patient) {
            return response()->json([
                'user' => [
                    'nama' => $kunjungan->patient->nama,
                    'alamat' => $kunjungan->patient->alamat,
                    'tanggal_lahir' => $kunjungan->patient->tanggal_lahir,
                    'jenis_kelamin' => $kunjungan->patient->jenis_kelamin,
                    'umur' => $kunjungan->patient->umur
                ],
                'kunjungan' => [
                    'id' => $kunjungan->id,
                    'nomor_kunjungan' => $kunjungan->nomor_kunjungan,
                    'tanggal_kunjungan' => $kunjungan->tanggal_kunjungan,
                    'keluhan' => $kunjungan->keluhan,
                    'tindakan' => $kunjungan->tindakan,
                    'biaya_pembayaran' => $kunjungan->biaya_pembayaran
                ]
            ]);
        }
    }

    return response()->json(['message' => 'Kunjungan tidak ditemukan'], 404);
}
    public function deleteKunjungan(Request $request, $type, $id)
    {
        if (strtolower($type) === 'medis') {
            $medic = Medic::find($id);
            if ($medic) {
                $medic->delete();
                return response()->json([
                    'message' => 'Kunjungan medis berhasil dihapus.',
                ]);
            }
        } elseif (strtolower($type) === 'non-medis') {
            $nonmedic = Nonmedics::find($id);
            if ($nonmedic) {
                $nonmedic->delete();
                return response()->json([
                    'message' => 'Kunjungan non-medis berhasil dihapus.',
                ]);
            }
        }

        return response()->json([
            'message' => 'Data kunjungan tidak ditemukan.',
        ], 404);
    }
}

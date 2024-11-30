<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Medic extends Model
{
    use HasFactory;
    protected $table = 'medics';
    protected $fillable = [
        'nomor_kunjungan',
        'tanggal_kunjungan',
        'nama_pasien',
        'anamnesa',
        'hasil_diagnosa',
        'tindakan',
    ];

    /**
     * The attributes that should be cast to native types.
     */
    protected $casts = [
        'tanggal_kunjungan' => 'date',
    ];
}

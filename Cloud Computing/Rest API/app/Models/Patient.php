<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Patient extends Model
{
    use HasFactory;

    protected $fillable = [
        'nama',
        'nik',
        'tanggal_lahir',
        'agama',
        'alamat',
        'jenis_kelamin',
        'nomor_hp',
    ];
}

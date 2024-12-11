<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class objektif extends Model
{
    protected $table = 'objektif';

    protected $fillable = [
        'berat_badan',
        'tinggi_badan',
        'tensi_darah',
        'nadi',
        'temp'
    ];
    use HasFactory;
}

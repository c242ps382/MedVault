<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Berita extends Model
{
    use HasFactory;

    protected $table = 'berita';

    protected $fillable = [
        'update',
        'title',
        'imageurl',
        'infotitle',
        'description',
    ];

    protected $casts = [
        'update' => 'date',
    ];

    public function getUpdateAttribute($value)
    {
        return \Carbon\Carbon::parse($value)->format('d-m-Y');
    }

}

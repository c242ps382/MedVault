<?php

namespace App\Models;

// use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Sanctum\HasApiTokens;

class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */

     //https://docs.google.com/document/d/14rIEzQMADtHug_AckXiOrHOpFW3xH4bk6osKxdT9pWc/edit?tab=t.0
     //https://www.youtube.com/watch?v=vQPeQcb4clQ
    protected $fillable = [
        'name',
        'email',
        'password',
        'alamat',
        'username',
        'imgprofile',
    ];

    protected $table = 'users';
    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    /**
     * The attributes that should be cast.
     *
     * @var array<string, string>
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    public function getUsers()
{
    $users = User::select('id', 'username')->get();
    return response()->json($users);
}

public function medis()
{
    return $this->hasOne(Medic::class, 'nama_pasien', 'username');
}

public function nonmedis()
{
    return $this->hasOne(Nonmedics::class, 'nama_pasien', 'username');
}


}

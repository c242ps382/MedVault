<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class RegisterController extends Controller
{
    /**
     * Handle the incoming request.
     */
    public function __invoke(Request $request)
    {
        // Validasi input dari user
        $request->validate([
            'name' => ['required', 'min:3'],
            'email' => ['required', 'email', 'unique:users,email'],
            'password' => ['required', 'min:8'],
            'username' => ['required', 'min:3', 'unique:users'],
            'alamat' => ['required', 'min:5'],
        ]);

        // Buat user baru di database
        $registered = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'username' => $request->username,
            'alamat' => $request->alamat,
        ]);

        // Generate token untuk autentikasi API
        $token = $registered->createToken('todo-api')->plainTextToken;

        // Kembalikan token dan data pengguna dalam format JSON
        return response()->json([
            'message' => 'User registered successfully.', // Tambahkan pesan sukses
            'token' => $token, // Token autentikasi
            'user' => [
                'id' => $registered->id,
                'name' => $registered->name,
                'email' => $registered->email,
                'username' => $registered->username,
                'alamat' => $registered->alamat,
                'imgprofile' => $registered->imgprofile,
            ],
        ], 201);
    }
}

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

        $request->validate([
            'name' => ['required','min:3'],
            'email' => ['required','email', 'unique:users,email'],
            'password' => ['required', 'min:8'],
            'username' => ['required','min:3','unique:users'],
            'alamat' =>['required','min:5']
        ]);

        $registered = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'username' => $request->username,
            'alamat' => $request->alamat
        ]);

        $token = $registered->createToken('todo-api')->plainTextToken;

        return response()->json([
            'token' => $token
        ]);
    }
}

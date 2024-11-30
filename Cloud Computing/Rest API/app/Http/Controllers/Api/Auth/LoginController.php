<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\User;

class LoginController extends Controller
{
    /**
     * Handle the incoming request.
     */
    public function __invoke(Request $request)
    {
        $credentials = $request->validate([
            'identifier' => ['required'], // Bisa username atau email
            'password' => ['required', 'min:8'],
        ]);

        // Cari pengguna berdasarkan username atau email
        $user = User::where('email', $credentials['identifier'])
            ->orWhere('username', $credentials['identifier'])
            ->first();

        if ($user && Auth::attempt(['email' => $user->email, 'password' => $credentials['password']])) {
            // Generate token
            $token = $user->createToken('todo-api')->plainTextToken;

            // Kembalikan token dalam format JSON
            return response()->json([
                'token' => $token
            ]);
        }

        return response()->json([
            'error' => 'Your credentials do not match'
        ], 401);
    }
}

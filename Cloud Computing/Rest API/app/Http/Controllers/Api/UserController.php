<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Storage;

class UserController extends Controller
{
    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, $id)
    {

        $user = User::findOrFail($id);
        // Validasi input
        $validated = $request->validate([
            'name' => 'nullable|string|max:255',
            'email' => 'nullable|email|unique:users,email,' . $id,
            'alamat' => 'nullable|string|max:255',
            'username' => 'nullable|string|unique:users,username,' . $id,
            'imgprofile' => 'nullable|image|mimes:jpg,jpeg,png|max:20480', // Validasi gambar profil
            'password' => 'nullable|string|min:8', // Pastikan password dikonfirmasi jika ada
        ]);

        // Cari pengguna berdasarkan ID


        // Perbarui password jika ada
        if ($request->filled('password')) {
            $validated['password'] = Hash::make($request->password);
        }

        // Perbarui gambar profil jika ada
        if ($request->hasFile('imgprofile')) {
            // Hapus gambar lama jika ada
            if ($user->imgprofile) {
                Storage::disk('gcs')->delete($user->imgprofile); // Menghapus gambar lama dari GCS
            }

            // Dapatkan nama file asli
            $fileName = $request->file('imgprofile')->getClientOriginalName();

            // Simpan gambar baru dengan nama file asli ke GCS
            $path = $request->file('imgprofile')->storeAs('profile-images', $fileName, 'gcs');

            // Dapatkan URL publik dari gambar yang disimpan
            $validated['imgprofile'] = Storage::disk('gcs')->url($path);
        }

        // Perbarui data pengguna
        $user->update($validated);

        return response()->json([
            'message' => 'User updated successfully',
            'user' => $user
        ], 200);
    }
}

<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Berita;
use Google\Cloud\Storage\StorageClient;
use Illuminate\Support\Facades\Storage;
class BeritaController extends Controller
{
    /**
     * Handle the incoming request.
     */
    public function index()
    {
        $berita = Berita::all();

        // Kembalikan data dalam bentuk JSON
        return response()->json([
            'message' => 'Data fetched successfully',
            'data' => $berita,
        ], 200);

    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'update' => 'required|string',
            'title' => 'required|string|max:255',
            'imageurl' => 'required|image|mimes:jpeg,png,jpg|max:20480',
            'infotitle' => 'required|string|max:255',
            'description' => 'required|string',
        ]);

        // Proses upload file ke Google Cloud Storage tanpa service-account.json
        if ($request->hasFile('imageurl')) {
            $file = $request->file('imageurl');
            $fileName = uniqid() . '.' . $file->getClientOriginalExtension();

            // Inisialisasi Google Cloud Storage Client
            $storage = new StorageClient([
                'projectId' => env('GOOGLE_CLOUD_PROJECT_ID'),
            ]);

            // Akses bucket
            $bucket = $storage->bucket(env('GOOGLE_CLOUD_STORAGE_BUCKET'));

            // Upload file ke bucket
            $bucket->upload(
                fopen($file->getPathname(), 'r'), // Stream file
                ['name' => "gambar_berita/$fileName"] // Lokasi di bucket
            );

            // URL file yang bisa diakses publik
            $validatedData['imageurl'] = "https://storage.googleapis.com/" . env('GOOGLE_CLOUD_STORAGE_BUCKET') . "/gambar_berita/$fileName";
        }

        // Simpan data ke database
        $berita = Berita::create($validatedData);

        return response()->json([
            'message' => 'News successfully created',
            'data' => $berita,
        ], 201);
    }

}

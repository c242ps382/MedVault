<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('berita', function (Blueprint $table) {
            $table->id(); // Primary Key
            $table->date('update'); // Kolom untuk update
            $table->string('title'); // Kolom untuk judul berita
            $table->string('imageurl'); // Kolom untuk URL gambar
            $table->string('infotitle'); // Kolom untuk judul informasi tambahan
            $table->string('description'); // Kolom untuk deskripsi informasi tambahan
            $table->timestamps(); // Kolom created_at dan updated_at
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('berita');
    }
};

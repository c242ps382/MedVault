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
        Schema::create('nonmedics', function (Blueprint $table) {
            $table->id();
            $table->string('nomor_kunjungan')->unique(); // Nomor kunjungan unik
            $table->date('tanggal_kunjungan'); // Tanggal kunjungan
            $table->string('nama_pasien'); // Nama pasien
            $table->text('keluhan'); // Keluhan (opsional)
            $table->text('tindakan'); // Tindakan (opsional)
            $table->decimal('biaya_pembayaran', 15, 2)->default(0); // Biaya pembayaran dalam IDR
            $table->timestamps(); // Kolom created_at dan updated_at
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('nonmedics');
    }
};

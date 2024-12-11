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
        Schema::create('medics', function (Blueprint $table) {
            $table->id();
            $table->string('nomor_kunjungan')->unique();
            $table->date('tanggal_kunjungan');
            $table->string('nama_pasien');
            $table->string('gejala');
            $table->json('anamnesa')->nullable();
            $table->text('hasil_diagnosa');
            $table->text('tindakan');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('medics');
    }
};

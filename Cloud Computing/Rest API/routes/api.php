<?php

use App\Http\Controllers\Api\Auth\RegisterController;
use App\Http\Controllers\Api\Auth\LoginController;
use App\Http\Controllers\Api\Auth\LogoutController;
use App\Http\Controllers\Api\KunjunganController;
use App\Http\Controllers\Api\MedicController;
use App\Http\Controllers\Api\BeritaController;
use App\Http\Controllers\Api\NonmedicController;
use App\Http\Controllers\Api\PatientController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;


Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
Route::put('user/update/{id}', [UserController::class, 'update']); // Update Profil
Route::post('register', RegisterController::class); // Registrasi
Route::post('login', LoginController::class); //Login
Route::post('logout', App\Http\Controllers\Api\Auth\LogoutControlller::class)->middleware('auth:sanctum'); // Logout


Route::get('user/update', function () {
    return view('update');
});

Route::get('patients', [PatientController::class, 'index']); // Show Data Patient
Route::post('patients/store', [PatientController::class, 'store']); // Add Data Patient
Route::put('patients/update/{id}', [PatientController::class, 'update']); // Update Data Patient
Route::delete('patients/delete/{id}', [PatientController::class, 'destroy']); // Delete Data Patient


Route::post('medics/store', [MedicController::class, 'store']); // Add Data Medis
Route::put('medics/update/{id}', [MedicController::class, 'update']); // Update Data Medis
Route::get('medics/patients/options', [MedicController::class, 'getPatientOptions']);

Route::post('nonmedics/store', [NonmedicController::class, 'store']); // Add Data Non Medis
Route::get('nonmedics/patients/options', [NonmedicController::class, 'getPatientOptions']);
Route::put('nonmedics/update/{id}', [NonmedicController::class, 'update']); // Update Data Non Medis

Route::get('kunjungan', [KunjunganController::class, 'getAllKunjungan']); // Show Data Medis dan Non Medis
Route::delete('kunjungan/{type}/{id}', [KunjunganController::class, 'deleteKunjungan']);
Route::get('kunjungandetail/{type}/{id}', [KunjunganController::class, 'getKunjunganDetail']);


Route::get('berita', [BeritaController::class, 'index']);
Route::get('berita/upload', function () {
    return view('berita');
});
Route::post('berita/store', [BeritaController::class, 'store']);
/*

Route = https://laravel-923957567288.asia-southeast2.run.app/

/user/update/{id} = Update Profil
/register = Registrasi
/login = Login
/logout = Logout

/patients = Show data Patient
/patients/store = Add data Patient
/patients/update/{id} = Update Patient
/patients/delete/{id} = Delete Patient

/medics/store = Add data Medis
/medics/update/{id} = Update Data Medis
/medics/patients/options = Filter username di form medis

/nonmedics/store = Add Data NonMedis
/nonmedics/patients/options = Filter username di form non medis
/nonmedics/update/{id} = Update Data Non Medis

/kunjungan = Show Data Medis dan Non Medis

*/

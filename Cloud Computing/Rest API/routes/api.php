<?php

use App\Http\Controllers\Api\Auth\RegisterController;
use App\Http\Controllers\Api\Auth\LoginController;
use App\Http\Controllers\Api\MedicController;
use App\Http\Controllers\Api\NonmedicController;
use App\Http\Controllers\Api\TodoController;
use App\Http\Controllers\Api\PatientController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;


Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('register', App\Http\Controllers\Api\Auth\RegisterController::class);
Route::post('login', App\Http\Controllers\Api\Auth\LoginController::class);
Route::post('patients', [PatientController::class, 'store']);
Route::post('medics', [MedicController::class, 'store']);
Route::post('nonmedics', [NonmedicController::class, 'store']);
Route::middleware('auth:sanctum')->put('user', [UserController::class, 'update']);



Route::apiResource('todos', \App\Http\Controllers\Api\TodoController::class)->middleware('auth:sanctum');

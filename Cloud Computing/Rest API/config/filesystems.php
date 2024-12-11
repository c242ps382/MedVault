<?php

return [

    'default' => env('FILESYSTEM_DISK', 'local'),


    'disks' => [

        'local' => [
            'driver' => 'local',
            'root' => storage_path('app'),
            'throw' => false,
        ],

        'public' => [
            'driver' => 'local',
            'root' => storage_path('app/public'),
            'url' => env('APP_URL') . '/storage',
            'visibility' => 'public',
            'throw' => false,
        ],

       'gcs' => [
    'driver' => 'gcs',
    'key_file_path' => null,
    'project_id' => env('GOOGLE_CLOUD_PROJECT_ID', 'your-project-id'),
    'bucket' => env('GOOGLE_CLOUD_STORAGE_BUCKET', 'your-bucket'),
    'storage_api_uri' => env('GOOGLE_CLOUD_STORAGE_API_URI', null),
    'apiEndpoint' => env('GOOGLE_CLOUD_STORAGE_API_ENDPOINT', null),
    'visibility' => 'public',
    'metadata' => ['cacheControl' => 'public,max-age=86400'],
    'throw' => true,
],

        's3' => [
            'driver' => 's3',
            'key' => env('AWS_ACCESS_KEY_ID'),
            'secret' => env('AWS_SECRET_ACCESS_KEY'),
            'region' => env('AWS_DEFAULT_REGION'),
            'bucket' => env('AWS_BUCKET'),
            'url' => env('AWS_URL'),
            'endpoint' => env('AWS_ENDPOINT'),
            'use_path_style_endpoint' => env('AWS_USE_PATH_STYLE_ENDPOINT', false),
            'throw' => false,
        ],

    ],

    /*
    |--------------------------------------------------------------------------
    | Symbolic Links
    |--------------------------------------------------------------------------
    |
    | Here you may configure the symbolic links that will be created when the
    | `storage:link` Artisan command is executed. The array keys should be
    | the locations of the links and the values should be their targets.
    |
    */

    'links' => [
        public_path('storage') => storage_path('app/public'),
    ],

];

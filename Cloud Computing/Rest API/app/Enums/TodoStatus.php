<?php

namespace App\Enums;

enum TodoStatus:string

{
    case Todo = 'Todo';
    case ON_PROGRESS = 'On Progress';
    case DONE = 'Done';
}

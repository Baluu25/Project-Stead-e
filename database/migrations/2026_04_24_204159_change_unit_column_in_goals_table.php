<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\DB;

return new class extends Migration
{
    public function up(): void
    {
        // MySQL-specifikus ALTER nem futtatható SQLite alatt (pl. tesztkörnyezetben)
        if (DB::getDriverName() !== 'sqlite') {
            DB::statement("ALTER TABLE goals MODIFY COLUMN unit VARCHAR(50) NOT NULL DEFAULT 'times'");
        }
    }

    public function down(): void
    {
        if (DB::getDriverName() !== 'sqlite') {
            DB::statement("ALTER TABLE goals MODIFY COLUMN unit ENUM('days','times','km','books','minutes','custom') NOT NULL DEFAULT 'times'");
        }
    }
};
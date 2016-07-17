package com.os.operando.sqlite.json1extension;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SampleSQLite sampleSqlite = new SampleSQLite(this);
        SQLiteDatabase sqLiteDatabase = sampleSqlite.getWritableDatabase();
        DatabaseUtils.dumpCursor(sqLiteDatabase.rawQuery("SELECT * FROM json_test_table", null));
        sqLiteDatabase.execSQL("insert into json_test_table(name,json) values(\"test\",\"test\")");
        sqLiteDatabase.execSQL("INSERT INTO json_test_table (\n" +
                "    name,\n" +
                "    json\n" +
                ")\n" +
                "VALUES (\n" +
                "    'test_name_1',\n" +
                "    json(json_object(\n" +
                "        'key_for_int', 1,\n" +
                "        'key_for_str', 'test_str',\n" +
                "        'key_for_arr', json('[1, \"a\", 2, \"b\"]'), -- json()で囲わないと文字列として保存される\n" +
                "        'key_for_obj', json('{\"c\":3, \"d\":4}'),\n" +
                "        'attr_a',      'nice_attr'\n" +
                "    ))\n" +
                ");");
    }

    private static class SampleSQLite extends SQLiteOpenHelper {

        private static final String name = "test";

        public SampleSQLite(Context context) {
            super(context, name, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE json_test_table (\n" +
                    "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name VARCHAR,\n" +
                    "    json JSON -- NEW!\n" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}

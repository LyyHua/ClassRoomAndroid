import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.classroom.screen.class_list.ClassDetail

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_CLASSES_TABLE)
        db.execSQL(CREATE_STUDENTS_TABLE)
        seedInitialData(db)
    }

    private fun seedInitialData(db: SQLiteDatabase) {
        val initialClasses = listOf(
            ClassDetail("MA006.O111", "Toan dai cuong"),
            ClassDetail("SS006.P19", "Phap luat dai cuong"),
            ClassDetail("SS004.P14", "Ky nang nghe nghiep")
        )
        initialClasses.forEach { classDetail ->
            val values = ContentValues().apply {
                put(COLUMN_CLASS_ID, classDetail.id)
                put(COLUMN_CLASS_NAME, classDetail.className)
            }
            db.insert(TABLE_CLASSES, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLASSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "classroom.db"

        const val TABLE_CLASSES = "classes"
        const val COLUMN_CLASS_ID = "id"
        const val COLUMN_CLASS_NAME = "className"

        const val TABLE_STUDENTS = "students"
        const val COLUMN_STUDENT_ID = "studentId"
        const val COLUMN_STUDENT_NAME = "name"
        const val COLUMN_STUDENT_DOB = "dob"
        const val COLUMN_STUDENT_CLASS_ID = "classId"

        private const val CREATE_CLASSES_TABLE = "CREATE TABLE $TABLE_CLASSES (" +
                "$COLUMN_CLASS_ID TEXT PRIMARY KEY, " +
                "$COLUMN_CLASS_NAME TEXT)"

        private const val CREATE_STUDENTS_TABLE = "CREATE TABLE $TABLE_STUDENTS (" +
                "$COLUMN_STUDENT_ID TEXT PRIMARY KEY, " +
                "$COLUMN_STUDENT_NAME TEXT, " +
                "$COLUMN_STUDENT_DOB TEXT, " +
                "$COLUMN_STUDENT_CLASS_ID TEXT, " +
                "FOREIGN KEY($COLUMN_STUDENT_CLASS_ID) REFERENCES $TABLE_CLASSES($COLUMN_CLASS_ID))"
    }
}
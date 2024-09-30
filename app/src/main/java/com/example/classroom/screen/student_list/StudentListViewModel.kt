data class Student(val id: Int, val studentId: String, val name: String, val dob: String)

var classStudentMap = mapOf(
    0 to listOf(

    ),
    1 to listOf(
        Student(id = 1, studentId = "23520906", name = "Hua Van Ly", dob = "18/08/2005"),
        Student(id = 2, studentId = "23520915", name = "Ho Nguyen Minh", dob = "25/02/2005")
    ),
    2 to listOf(
        Student(id = 3, studentId = "23520917", name = "Tran Quang Manh", dob = "13/05/2005"),
        Student(id = 4, studentId = "23520921", name = "Phan Duc Minh", dob = "06/09/2005")
    ),
    3 to listOf(
        Student(id = 5, studentId = "23520922", name = "Nguyen Van A", dob = "01/01/2005"),
        Student(id = 6, studentId = "23520923", name = "Le Thi B", dob = "02/02/2005")
    ),
    4 to listOf(
    )
)
package mx.com.yourlawyer.practica1.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.com.yourlawyer.practica1.util.Constants

@Entity(tableName = Constants.DATABASE_LAWYERS_TABLE)
class LawyerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lawyer_id")
    var id: Long = 0,
    @ColumnInfo(name = "lawyer_category")
    var category: String = "",
    @ColumnInfo(name = "lawyer_subcategory")
    var subcategory: String = "",
    @ColumnInfo(name = "lawyer_image")
    var image: String = "",
    @ColumnInfo(name = "lawyer_active")
    var activeLawyers: Int = 0,
    @ColumnInfo(name = "lawyer_description")
    var description: String = "",
    @ColumnInfo(name = "lawyer_examples", defaultValue = "Enter examples of subcategory")
    var examples: String = ""

)
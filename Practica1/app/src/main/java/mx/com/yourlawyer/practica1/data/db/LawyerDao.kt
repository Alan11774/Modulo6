package mx.com.yourlawyer.practica1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.util.Constants

@Dao
interface LawyerDao {
    @Insert
    suspend fun insertLawyer(lawyer: LawyerEntity)
    @Insert
    suspend fun insertGames(games: MutableList<LawyerEntity>)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_LAWYERS_TABLE}")
    suspend fun getAllGames(): MutableList<LawyerEntity>

    //Update
    @Update
    suspend fun updateGame(game: LawyerEntity)

    //Delete
    @Delete
    suspend fun deleteGame(game: LawyerEntity)
}
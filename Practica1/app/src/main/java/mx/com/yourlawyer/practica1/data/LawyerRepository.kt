package mx.com.yourlawyer.practica1.data

import mx.com.yourlawyer.practica1.data.db.LawyerDao
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity

class LawyerRepository(
    private val lawyerDao: LawyerDao
) {
    suspend fun insertLawyer(lawyer: LawyerEntity) {
        lawyerDao.insertLawyer(lawyer)
    }

    suspend fun getAllLawyers(): MutableList<LawyerEntity> = lawyerDao.getAllGames()

    suspend fun updateLawyer(game: LawyerEntity){
        lawyerDao.updateGame(game)
    }

    suspend fun deleteLawyer(game: LawyerEntity){
        lawyerDao.deleteGame(game)
    }

}
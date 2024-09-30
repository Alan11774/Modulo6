package mx.com.yourlawyer.practica1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.util.Constants


// This script is for:
// - Create the database
// - Define the entities that will be part of the database
// - Define the version of the database
// - Define the export schema
// - Create the companion object to create the database
// - Create the getDatabase function to get the instance of the database
// - Create the singleton pattern to get the instance of the database
// - Create the abstract function to get the DAO

@Database(
    entities = [LawyerEntity::class],
    version = 1, // version of the database for migrations
    exportSchema = true // by default is true.
)

abstract class LawyerDatabase: RoomDatabase() {
    // Here goes the DAO
    abstract fun lawyerDao(): LawyerDao
    companion object {

        @Volatile
        private var INSTANCE: LawyerDatabase? = null

        fun getDatabase(context: Context): LawyerDatabase {
            // If the instance is not null, then we are going
            // to return the one we already have
            // If it is null, we create an instance and return it
            // (singleton pattern)

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LawyerDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }

        }

    }


}
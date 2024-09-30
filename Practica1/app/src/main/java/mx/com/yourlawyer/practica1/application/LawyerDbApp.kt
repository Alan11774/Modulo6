package mx.com.yourlawyer.practica1.application

import android.app.Application
import mx.com.yourlawyer.practica1.data.LawyerRepository
import mx.com.yourlawyer.practica1.data.db.LawyerDatabase

// This script is for
// - Create the application class
// - Create the database instance
// - Create the repository instance
// - Create the singleton pattern to get the instance of the database

class LawyerDbApp(): Application() {
    private val database by lazy{
        LawyerDatabase.getDatabase(this@LawyerDbApp)
    }

    val repository by lazy {
        LawyerRepository(database.lawyerDao())
    }
}
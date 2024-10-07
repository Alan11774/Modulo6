package mx.com.yourlawyer.practica2.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import mx.com.yourlawyer.practica2.R
import mx.com.yourlawyer.practica2.databinding.ActivityMainBinding
import mx.com.yourlawyer.practica2.ui.fragments.LawyersListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Mostramos el fragment inicial GamesListFragment
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LawyersListFragment())
                .commit()
        }

    }

}
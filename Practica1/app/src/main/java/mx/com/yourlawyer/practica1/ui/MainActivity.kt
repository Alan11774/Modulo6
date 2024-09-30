package mx.com.yourlawyer.practica1.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mx.com.yourlawyer.practica1.R
import mx.com.yourlawyer.practica1.application.LawyerDbApp
import mx.com.yourlawyer.practica1.data.LawyerRepository
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lawyers: MutableList<LawyerEntity> = mutableListOf()
    private lateinit var repository: LawyerRepository
    private lateinit var lawyerAdapter: LawyerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as LawyerDbApp).repository
        lawyerAdapter = LawyerAdapter{ selectedLawyer ->


            val dialog = LawyerDialog(newLawyer = false, lawyer = selectedLawyer, updateUI = {
                updateUI()
            }, message = { text ->
                message(text)
            })

            dialog.show(supportFragmentManager, getString(R.string.dialog2))

        }

        binding.rvLawyers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = lawyerAdapter
        }



        updateUI()

    }

    fun click(view: View) {

        val dialog = LawyerDialog(updateUI = {
            updateUI()
        }, message = { text ->
            //Aquí va el mensaje
            message(text)

        })


        dialog.show(supportFragmentManager, getString(R.string.dialog1))

    }

    private fun message(text: String){


        Snackbar.make(
            binding.cl,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.snackbar))
            .show()

    }

    private fun updateUI(){
        lifecycleScope.launch {
            lawyers = repository.getAllLawyers()

            binding.tvNoRecords.visibility =
                if(lawyers.isNotEmpty()) View.INVISIBLE else View.VISIBLE

            lawyerAdapter.updateList(lawyers)
        }
    }
}
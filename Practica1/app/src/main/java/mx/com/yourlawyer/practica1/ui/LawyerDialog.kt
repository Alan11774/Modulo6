package mx.com.yourlawyer.practica1.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.com.yourlawyer.practica1.R
import mx.com.yourlawyer.practica1.application.LawyerDbApp
import mx.com.yourlawyer.practica1.data.LawyerRepository
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.databinding.LawyerDialogBinding
import java.io.IOException

// This script is for
// - Create the LawyerDialog class
// - Create the LawyerDialogBinding class
// - Create the LawyerDialog class to show the dialog to add, update or delete a lawyer
// - Create the LawyerDialogBinding class to bind the layout of the dialog


class LawyerDialog(
    private val newLawyer: Boolean = true,
    private var lawyer: LawyerEntity = LawyerEntity(
        category = "",
        subcategory = "",
        image = "",
        activeLawyers = 0,
        description = "",
        examples = ""
    ),
    private val updateUI: () -> Unit,
    private val message: (String) -> Unit
): DialogFragment() {
    private var _binding: LawyerDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var repository: LawyerRepository


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = LawyerDialogBinding.inflate(requireActivity().layoutInflater)

        //Obtenemos dentro del dialog fragment una instancia al repositorio
        repository = (requireContext().applicationContext as LawyerDbApp).repository

        builder = AlertDialog.Builder(requireContext())

        //Establecemos en los text input edit text los valores del objeto game
        binding.apply {
            tietCategory.setText(lawyer.category)
            tietSubcategory.setText(lawyer.subcategory)
            tietActiveLawyers.setText(lawyer.activeLawyers.toString())
        }

        dialog = if(newLawyer)
            buildDialog(getString(R.string.save), getString(R.string.cancel), {
                //Acción de guardar

                //Obtenemos los textos ingresados y se los
                //asignamos a nuestro objeto game
                binding.apply {
                    lawyer.apply {
                        category = tietCategory.text.toString()
                        subcategory = tietSubcategory.text.toString()
                        activeLawyers = tietActiveLawyers.text.toString().toInt() // The type of data is Int
                    }
                }

                try{

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async {
                            repository.insertLawyer(lawyer)
                        }

                        //Con esto nos esperamos a que se termine esta acción antes de ejecutar lo siguiente
                        result.await()

                        //Con esto mandamos la ejecución de message y updateUI al hilo principal
                        withContext(Dispatchers.Main) {
                            message(getString(R.string.lawyer_saved_successfully))

                            updateUI()
                        }
                    }



                }catch (e: IOException){

                    message(getString(R.string.error_saving_the_lawyer))

                }

            }, {
                //Acción de cancelar

            })
        else
            buildDialog(getString(R.string.update), getString(R.string.erase), {
                binding.apply {
                    lawyer.apply {
                        category = tietCategory.text.toString()
                        subcategory = tietSubcategory.text.toString()
                        activeLawyers = tietActiveLawyers.text.toString().toInt() // The type of data is Int
                    }
                }

                try{

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async {
                            repository.updateLawyer(lawyer)
                        }

                        result.await()

                        withContext(Dispatchers.Main){
                            message(getString(R.string.lawyer_updated_successfully))

                            updateUI()
                        }
                    }



                }catch (e: IOException){

                    message( getString(R.string.error_updating_the_lawyer))

                }

            }, {

                val context = requireContext()

                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.confirm_lawyer, lawyer.category))
                    .setPositiveButton(getString(R.string.ok)){ _, _ ->
                        try{
                            lifecycleScope.launch(Dispatchers.IO) {

                                val result = async {
                                    repository.deleteLawyer(lawyer)
                                }

                                result.await()

                                withContext(Dispatchers.Main){

                                    message(context.getString(R.string.lawyer_removed))

                                    updateUI()
                                }
                            }


                        }catch (e: IOException){

                            message(getString(R.string.error_lawyer_delete))

                        }
                    }
                    .setNegativeButton(getString(R.string.cancel)){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create().show()

            })



        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        //Debido a que la clase dialog no me permite referenciarme a sus botones
        val alertDialog = dialog as AlertDialog

        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

        saveButton?.isEnabled = false


        binding.apply {
            setupTextWatcher(
                tietCategory,
                tietSubcategory,
                tietActiveLawyers
            )
        }

    }


    private fun validateFields(): Boolean
            = binding.tietCategory.text.toString().isNotEmpty() &&
            binding.tietSubcategory.text.toString().isNotEmpty() &&
            binding.tietActiveLawyers.text.toString().isNotEmpty()


    private fun setupTextWatcher(vararg textFields: TextInputEditText){
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        }

        textFields.forEach { textField ->
            textField.addTextChangedListener(textWatcher)
        }
    }


    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit
    ): Dialog =
        builder.setView(binding.root)
            .setTitle(R.string.lawyer)
            .setPositiveButton(btn1Text){ _, _ ->
                //Acción para el botón positivo
                positiveButton()
            }.setNegativeButton(btn2Text){ _, _ ->
                //Acción para el botón negativo
                negativeButton()
            }
            .create()


}
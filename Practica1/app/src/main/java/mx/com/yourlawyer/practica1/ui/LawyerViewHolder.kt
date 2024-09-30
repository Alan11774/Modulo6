package mx.com.yourlawyer.practica1.ui

import androidx.recyclerview.widget.RecyclerView
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.databinding.LawyerElementBinding

class LawyerViewHolder(
    private val binding: LawyerElementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lawyer: LawyerEntity){
        binding.apply {
            tvCategory.text = lawyer.category
            tvSubcategory.text = lawyer.subcategory
            tvActiveLawyers.text = lawyer.activeLawyers.toString()
        }
    }
}
package mx.com.yourlawyer.practica2.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.com.yourlawyer.practica2.data.remote.model.LawyerDto
import mx.com.yourlawyer.practica2.databinding.LawyerElementBinding

class LawyerViewHolder (
    private val binding: LawyerElementBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(lawyer: LawyerDto){

        binding.tvTitle.text = lawyer.category

        //Con Glide
        Glide.with(binding.root.context)
            .load(lawyer.image)
            .into(binding.ivThumbnail)
        binding.tvDeveloper.text = lawyer.subcategory?.joinToString(", ")
        binding.tvReleased.text = lawyer.activeLawyers
        binding.tvRating.text = lawyer.id


        //Con Picasso
        /*Picasso.get()
            .load(game.thumbnail)
            .into(binding.ivThumbnail)*/
    }

}
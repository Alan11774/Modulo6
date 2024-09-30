package mx.com.yourlawyer.practica1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.yourlawyer.practica1.data.db.model.LawyerEntity
import mx.com.yourlawyer.practica1.databinding.LawyerElementBinding

class LawyerAdapter(
    private val onLawyerClicked: (LawyerEntity) -> Unit
): RecyclerView.Adapter<LawyerViewHolder>() {


    private var lawyers: MutableList<LawyerEntity> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawyerViewHolder {
        // this is for the LawyerViewHolder is needed to be created and
        // returned to the RecyclerView to be shown in the UI
        val binding = LawyerElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawyerViewHolder(binding)
    }
    // This is for the number of items that the RecyclerView will show
    override fun getItemCount(): Int = lawyers.size

    override fun onBindViewHolder(holder: LawyerViewHolder, position: Int) {
        val lawyer = lawyers[position]
        holder.bind(lawyer)
        holder.itemView.setOnClickListener {
            onLawyerClicked(lawyer)
        }
    }
    fun updateList(list: MutableList<LawyerEntity>){
        lawyers = list
        notifyDataSetChanged()
    }
}

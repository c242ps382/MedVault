package com.dicoding.medvault.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.medvault.R
import com.dicoding.medvault.model.Patient

class CustomAdapter(
    private val context: Context,
) : BaseAdapter() {

    private val items: MutableList<Patient> = mutableListOf()

    fun updateData(newItems: List<Patient>) {
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.spinner_item_nama_pasien, parent, false)

            holder = ViewHolder(
                imageView = view.findViewById(R.id.ivPerson),
                nameTextView = view.findViewById(R.id.tvNamaPasien),
                addressTextView = view.findViewById(R.id.tvAlamat)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val personItem = items[position]

        holder.nameTextView.text = personItem.name
        holder.addressTextView.text = personItem.address

        return view
    }

    private data class ViewHolder(
        val imageView: ImageView,
        val nameTextView: TextView,
        val addressTextView: TextView
    )
}

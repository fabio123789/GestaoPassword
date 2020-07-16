package com.example.passwords

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.passwords.room.Password
import kotlinx.android.synthetic.main.item_list.view.*

@Suppress("NAME_SHADOWING")
internal class PasswordAdapter(private val passwords: List<Password>) : RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent , false)
        )
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.textViewPassword.text = passwords[position].password
        holder.itemView.textViewSite.text = passwords[position].site
        holder.itemView.textViewDescricao.text = passwords[position].descricao
        holder.itemView.textViewDate.text = passwords[position].dateTime

        val activity = holder.itemView.context as Activity

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, PasswordActivity::class.java).apply {
                putExtra( "password", passwords[position].id)
            }

            activity.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}
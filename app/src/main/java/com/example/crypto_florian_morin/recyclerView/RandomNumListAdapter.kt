package com.example.crypto_florian_morin.recyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto_florian_morin.bdd.AccesLocal
import com.example.crypto_florian_morin.R
import com.google.android.material.snackbar.Snackbar


class RandomNumListAdapter(context: Context) : RecyclerView.Adapter<RecyclerViewHolder>() {

    var CryptoAChercher: String ="null"
    private var accesLocal = AccesLocal(context)

    override fun getItemViewType(position: Int): Int {
        return R.layout.frame_textview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var Actuel = accesLocal.getCryptoMonnaie(CryptoAChercher,position+1)
        if (Actuel != null) {
            holder.view.text=Actuel.ID+" "+Actuel.Name+" "+Actuel.Symbol
        }

    }

    override fun getItemCount(): Int {
        Log.e("CryptoAChercher",CryptoAChercher)
        return accesLocal.getSize(CryptoAChercher)-1
    }

    class ViewHolder(itemView: View, onNoteListener: OnNoteListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mOnNoteListener: OnNoteListener = onNoteListener
        override fun onClick(view: View?) {
            mOnNoteListener.onNoteClick(adapterPosition);
            if (view != null) {
                Snackbar.make(view, "Lecture base de donnée, wait", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}
package com.example.crypto_florian_morin.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto_florian_morin.*
import com.example.crypto_florian_morin.bdd.AccesLocal
import com.example.crypto_florian_morin.bdd.CryptoMonnaie
import com.example.crypto_florian_morin.recyclerView.ItemClickSupport
import com.example.crypto_florian_morin.recyclerView.RandomNumListAdapter
import com.example.crypto_florian_morin.recyclerView.SlideUpItemAnimator
import com.google.android.material.snackbar.Snackbar
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MenuDesCrypto() : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var root:View
    private lateinit var local: AccesLocal

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=inflater.inflate(R.layout.fragment_menu, parent, false)
        val RechercheButton= root.findViewById<Button>(R.id.ActiverReche)
        val CryptoAChercher = root.findViewById<EditText>(R.id.NomCrypto)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.itemAnimator = SlideUpItemAnimator()
        recyclerView.adapter = activity?.applicationContext?.let { RandomNumListAdapter(it) }
        (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher="";
        this.configureOnClickRecyclerView()
  //      return inflater.inflate(R.layout.fragment_pizza_menu, parent, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val RechercheButton= root.findViewById<Button>(R.id.ActiverReche)
        val CryptoAChercher = root.findViewById<EditText>(R.id.NomCrypto)
        val Refresh = root.findViewById<Button>(R.id.Refresh)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.itemAnimator = SlideUpItemAnimator()
        recyclerView.adapter = activity?.applicationContext?.let { RandomNumListAdapter(it) }
        (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher="";
        this.configureOnClickRecyclerView()

        RechercheButton.setOnClickListener {
            view ->Snackbar.make(view,"Vous cherchez :"+CryptoAChercher.text.toString(),Snackbar.LENGTH_LONG).setAction("Action", null).show()
            Log.e("Maj",CryptoAChercher.text.toString())
            recyclerView.adapter=null
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = activity?.applicationContext?.let { RandomNumListAdapter(it) }
            (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher=CryptoAChercher.text.toString()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        Refresh.setOnClickListener {
            view ->Snackbar.make(view,"Debut refresh des données",Snackbar.LENGTH_LONG).setAction("Action", null).show()
            lifecycleScope.launch  {
                GetInformation()
            }
            Snackbar.make(view,"Fin refresh des données",Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

    }


    private suspend fun GetInformation(){
        local = AccesLocal(context)
        local.deleteBdd()
        val data = CoinGeckoClient.create()
        var pos=0
        val Liste= data.getCoinList()
        Liste.forEach {
            Liste.size
            val Actual = CryptoMonnaie(it.id,it.name,it.symbol)
            local.ajout(Actual)
            if(pos==846) {
                val Bitcoin = data.getCoinById(it.id)
                data.getPrice(Bitcoin.id,"eur")
                Log.e("Detail", data.getPrice(Bitcoin.id,"eur").toString())
            }
            pos++
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.itemAnimator = SlideUpItemAnimator()
        recyclerView.adapter = activity?.applicationContext?.let { RandomNumListAdapter(it) }
        (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher="";
        recyclerView.adapter?.notifyDataSetChanged()
    }

        @RequiresApi(Build.VERSION_CODES.M)
    private fun configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.frame_textview).setOnItemClickListener {
                recyclerView, position, _ ->DisplayClick(position as Int)
        }
    }
    private fun DisplayClick(position:Int){
        var local = AccesLocal(context)

        val CryptoAChercher = root.findViewById<EditText>(R.id.NomCrypto)
        var Actuel = local.getCryptoMonnaie(CryptoAChercher.text.toString(), 1 + position)
        val fragment = CryptoDetail()
        val arguments = Bundle()
        arguments.putInt("Position", position+1)
        arguments.putString("Name",(recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher)
        fragment.arguments = arguments
        parentFragmentManager.beginTransaction()
            .replace((requireView().parent as ViewGroup).id, fragment)
            .addToBackStack(null)
            .commit()
    }
}
package com.example.crypto_florian_morin.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.crypto_florian_morin.R
import com.example.crypto_florian_morin.bdd.AccesLocal
import com.squareup.picasso.Picasso
import drewcarlson.coingecko.CoinGeckoClient
import io.ktor.client.features.*
import io.ktor.util.date.*
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL


class CryptoDetail : Fragment() {

    private var position = 0
    private var CryptoAChercher=""
    private var tvTitle: TextView? = null
    private var tvDetails2: TextView? = null
    private var RetourMenu: Button?=null
    private var ImageCrypto: ImageView?=null

    private lateinit var local: AccesLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            // Get back arguments
            if (arguments != null) {
                position = requireArguments().getInt("Position", 0)
                CryptoAChercher = requireArguments().getString("Name", "")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.fragment_detail, parent, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set values for view here
        local= AccesLocal(context)
        tvTitle = view.findViewById<View>(R.id.tvTitle) as TextView
        tvDetails2 = view.findViewById(R.id.tvDetails2) as TextView
        ImageCrypto = view.findViewById(R.id.imageView)
        RetourMenu= view.findViewById(R.id.RetourArriere) as Button

        // update view
        tvTitle!!.text = local.getCryptoMonnaie(CryptoAChercher,position)!!.Name


        val data = CoinGeckoClient.create()

    lifecycleScope.launch {

        try {
            local.getCryptoMonnaie(CryptoAChercher, position)!!.Name?.let {
            tvDetails2!!.text = (
                    "Name:"+data.getCoinById(it).name
                    +"\n\nDate de création:"+data.getCoinById(it).genesisDate
                    +"\n\nLe prix actuel est de "+data.getPrice(it, "eur")[local.getCryptoMonnaie(CryptoAChercher, position)!!.Name]?.getRawField("eur")+"euros"
                    +"\n\nDerniere MAJ des données:"+ data.getCoinById(it).lastUpdated
                    +"\n\nDescription: "+ data.getCoinById(it).description["fr"]+"\n\nRank:"+data.getCoinById(it).coingeckoRank.toString()
      //              +"\n\nOrigin:"+ data.getCoinById(it).categories[0]
                    )
                try {
                    Picasso.with(context).load(data.getCoinById(it).image.large).into(ImageCrypto);
                }
                catch (e: Exception) {
                    Log.e("Error Message", e.message.toString())
                    e.printStackTrace()
                }
            }
        }catch (e: ClientRequestException){
            tvDetails2!!.text ="Aucune information disponible"
        }
    }
        RetourMenu!!.setOnClickListener{
            val fragment = MenuDesCrypto()
            parentFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

}
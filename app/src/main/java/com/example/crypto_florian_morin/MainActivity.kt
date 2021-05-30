package com.example.crypto_florian_morin

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.crypto_florian_morin.bdd.AccesLocal
import com.example.crypto_florian_morin.bdd.CryptoMonnaie
import com.example.crypto_florian_morin.fragments.MenuDesCrypto
import drewcarlson.coingecko.CoinGeckoClient


class MainActivity : AppCompatActivity() {

    private lateinit var local: AccesLocal

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        lifecycleScope.launch  {
            GetInformation()
        }

 */


        // Instance of first fragment
        val firstFragment = MenuDesCrypto()

        // Add Fragment to FrameLayout (flContainer), using FragmentManager
        val ft = supportFragmentManager.beginTransaction() // begin  FragmentTransaction

        ft.add(R.id.flContainer, firstFragment) // add    Fragment

        ft.commit() // commit FragmentTransaction


  /*      recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(
            baseContext,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.itemAnimator = SlideUpItemAnimator()
        recyclerView.adapter = applicationContext?.let { RandomNumListAdapter(it) }
        (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher = "";
        this.configureOnClickRecyclerView()


        val RechercheButton = findViewById<Button>(R.id.ActiverReche)
        val CryptoAChercher = findViewById<EditText>(R.id.NomCrypto)

        RechercheButton.setOnClickListener {
            Log.e("Maj", CryptoAChercher.text.toString())
            recyclerView.adapter = null
            recyclerView.layoutManager = LinearLayoutManager(baseContext)
            recyclerView.adapter = applicationContext?.let { RandomNumListAdapter(it) }
            (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher =
                CryptoAChercher.text.toString()
            recyclerView.adapter?.notifyDataSetChanged()
        }

 */


    }
/*
    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.frame_textview)
            .setOnItemClickListener { recyclerView, position, v ->
                Snackbar.make(v, "Numéro $position", Snackbar.LENGTH_LONG).setAction("Action", null)
                    .show()
                /*        recyclerView.adapter=null
            recyclerView.layoutManager = LinearLayoutManager(baseContext)
            recyclerView.adapter =applicationContext?.let { RandomNumListAdapter(it) }
            recyclerView.adapter?.notifyDataSetChanged()
      */
                var local = AccesLocal(baseContext)
                var Actuel = local.getCryptoMonnaie(
                    (recyclerView.adapter as RandomNumListAdapter?)?.CryptoAChercher,
                    position + 1
                )

                Log.e("HomeChangementTexte", Actuel.toString())

                // Load Pizza Detail Fragment
            }
    }

 */
private suspend fun GetInformation() {
    local = AccesLocal(baseContext)
    local.deleteBdd()
    val data = CoinGeckoClient.create()
    var pos = 0

    val Liste = data.getCoinList()
    Liste.forEach {
        //      Log.e("List", "NUM="+pos.toString()+" |ID="+it.id +" |NAME= "+it.name+" |SYMBOL= "+it.symbol)
        Liste.size
        val Actual = CryptoMonnaie(it.id, it.name, it.symbol)
        local.ajout(Actual)
        if (pos == 846) {
            val Bitcoin = data.getCoinById(it.id)
            data.getPrice(Bitcoin.id, "eur")
            Log.e("Detail", data.getPrice(Bitcoin.id, "eur").toString())
        }
        pos++
    }
}
}
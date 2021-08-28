package br.com.dio.coinconverter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import br.com.dio.coinconverter.R
import br.com.dio.coinconverter.core.extensions.*
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ActivityMainBinding
import br.com.dio.coinconverter.presentation.MainViewModel
import br.com.dio.coinconverter.ui.history.HistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdaptersFrom()
        bindListerners()
        bindObserve()

        setSupportActionBar(binding.mainToobar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_action_history){
            bindClear()
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindListerners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConvert.isEnabled = it != null && it.toString().isNotEmpty()
            binding.btnSave.isEnabled = false
        }

        binding.btnConvert.setOnClickListener {
            it.hideSoftKeyboard()
            Log.e("COIN", binding.tvFrom.text.toString())
            Log.e("COIN", binding.tvTo.text.toString())

            if (binding.tvFrom.text.toString() == binding.tvTo.text.toString()){
                Toast.makeText(this,
                                "Não é possivel conveter de ${binding.tvFrom.text} para ${binding.tvTo.text}",
                                Toast.LENGTH_LONG).show()

            }else{
                viewModel.getExchangeValue("${binding.tvFrom.text}-${binding.tvTo.text}")
            }

        }

        binding.btnSave.setOnClickListener {
            val value = viewModel.state.value
            (value as? MainViewModel.State.Success)?.let {
                val exchange = it.value.copy(bid = it.value.bid * binding.tilValue.text.toDouble(),
                                             create_convert = DateTimeCurrent(),
                                             value = binding.tilValue.text.toDouble())
                viewModel.savedExchange(exchange)
            }
        }
        
        binding.tvFrom.setOnItemClickListener { parent, view, position, id ->
            bindAdaptersTo(parent.getItemAtPosition(position).toString())
        }
    }

    private fun bindAdaptersFrom() {
        val list = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.tvFrom.setAdapter(adapter)
    }

    private fun bindAdaptersTo(coin: String) {

        var list = emptyList<Coin>()

        if (coin.isNotEmpty()){
            list = Coin.values().filter {
                it.name != coin
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.tvTo.setAdapter(adapter)
    }

    private fun bindObserve() {
        viewModel.state.observe(this, Observer {
            when(it){
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.throwable.message)
                    }.show()
                }
                is MainViewModel.State.Success -> bindSuccess(it)
                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(getString(R.string.main_mensage_save))
                    }.show()
                }
            }
        })
    }

    private fun bindSuccess(it: MainViewModel.State.Success){
        dialog.dismiss()
        binding.btnSave.isEnabled = true

        val selectedCoin = binding.tilTo.text
        val coin = Coin.values().find {
            it.name == selectedCoin
        }?: Coin.BRL

        var result = it.value.bid * binding.tilValue.text.toDouble()
        binding.tvCoinResult.text = result.formatCurrency(coin.locale)
    }

    private fun bindClear(){
        binding.tilValue.text = ""
        binding.tvCoinResult.text = ""
        bindAdaptersFrom()
        bindAdaptersTo("")
    }

}
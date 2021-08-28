package br.com.dio.coinconverter.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.dio.coinconverter.R
import br.com.dio.coinconverter.core.extensions.createDialog
import br.com.dio.coinconverter.core.extensions.createProgressDialog
import br.com.dio.coinconverter.databinding.ActivityHistoryBinding
import br.com.dio.coinconverter.presentation.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private val adapter by lazy { HistoryListAdapter() }
    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HistoryViewModel>()
    private val dialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.historyToobar)
        binding.historyToobar.setTitle("Histórico")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvHistory.adapter = adapter
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )

        bindObserver()

        lifecycle.addObserver(viewModel)
    }

    private fun bindObserver() {
        viewModel.state.observe(this){
            when(it){
                HistoryViewModel.State.Loading -> dialog.show()
                is HistoryViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.throwable.message)
                    }
                }
                is HistoryViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.value)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
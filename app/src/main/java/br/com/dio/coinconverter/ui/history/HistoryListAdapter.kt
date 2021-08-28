package br.com.dio.coinconverter.ui.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.core.extensions.formatCurrency
import br.com.dio.coinconverter.core.extensions.formatDateBr
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import br.com.dio.coinconverter.databinding.ItemHistoryBinding
import br.com.dio.coinconverter.ui.history.HistoryListAdapter.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class HistoryListAdapter: ListAdapter<ExchangesResponseValue, ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ExchangesResponseValue){
            binding.tvName.text = item.name
            var coin = Coin.getByName(item.codein)
            binding.tvValue.text = item.bid.formatCurrency(coin.locale)

            var str: String = ""
            val dt = str.formatDateBr(item.create_convert)

            binding.tvDate.text = dt
        }
    }


}

class DiffCallback: DiffUtil.ItemCallback<ExchangesResponseValue>() {
    override fun areItemsTheSame(
        oldItem: ExchangesResponseValue,
        newItem: ExchangesResponseValue
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ExchangesResponseValue,
        newItem: ExchangesResponseValue
    ) = oldItem.id == newItem.id

}

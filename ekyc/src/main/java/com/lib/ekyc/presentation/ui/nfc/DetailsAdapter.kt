package com.lib.ekyc.presentation.ui.nfc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lib.ekyc.databinding.NfcDetailItemBinding
import com.lib.ekyc.presentation.utils.base.KeyValueEntity

class DetailsAdapter(private val items: List<KeyValueEntity>) :
    RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NfcDetailItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: NfcDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeyValueEntity) {
            with(binding) {
                key.text = item.key?.capitalize()
                value.text = item.value
            }
        }
    }
}
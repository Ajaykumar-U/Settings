package com.android.car.settings.presentation.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.car.settings.databinding.ItemSettingsCategoryBinding
import com.android.car.settings.domain.model.SettingsCategory

class SettingsRecyclerViewAdapter(
    private val onClickThumbListener: OnClickThumbListener
) : RecyclerView.Adapter<SettingsRecyclerViewAdapter.ThumbnailViewHolder>() {

    private var items: List<SettingsCategory> = emptyList()

    fun setList(list: List<SettingsCategory>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val binding = ItemSettingsCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    interface OnClickThumbListener {
        fun onThumbnailClick(item: SettingsCategory)
    }

    inner class ThumbnailViewHolder(
        private val binding: ItemSettingsCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.settingsIcon.setOnClickListener {
                triggerClick()
            }
            binding.settingsText.setOnClickListener {
                triggerClick()
            }
        }

        fun bind(setting: SettingsCategory) {
            binding.settingsIcon.setImageResource(setting.iconResId)
            binding.settingsText.text = setting.title
        }

        private fun triggerClick() {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onClickThumbListener.onThumbnailClick(items[position])
            }
        }
    }
}
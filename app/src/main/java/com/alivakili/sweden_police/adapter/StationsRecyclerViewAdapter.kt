package com.alivakili.sweden_police.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alivakili.sweden_police.model.StationsModel
import com.alivakili.sweden_police.databinding.StationItemListBinding

class StationsRecyclerViewAdapter (
    private val items: StationsModel?,
    private val onClicked: (StationsModel.StationsModelItem?) -> Unit,
    private val context: Context,
) : RecyclerView.Adapter<StationsRecyclerViewAdapter.StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            StationViewHolder {
        return StationViewHolder.create(parent, onClicked, context)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = items?.get(position)
        holder.bind(station)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class StationViewHolder(
        private val binding: StationItemListBinding,
        private val onClicked: (StationsModel.StationsModelItem?) -> Unit,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(station: StationsModel.StationsModelItem?) {
            binding.apply {
                stationName.text=station?.name
                place.text=station?.location?.name
                service.text=station?.services?.mapNotNull { it?.name }?.joinToString(", ")

                root.setOnClickListener(View.OnClickListener {
                    onClicked(station)
                })

            }
        }


        companion object {
            fun create(
                parent: ViewGroup,
                onClicked: (StationsModel.StationsModelItem?) -> Unit,
                context: Context
            ): StationViewHolder {
                val binding = StationItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return StationViewHolder(
                    binding = binding,
                    onClicked = onClicked,
                    context = context
                )
            }
        }
    }


}
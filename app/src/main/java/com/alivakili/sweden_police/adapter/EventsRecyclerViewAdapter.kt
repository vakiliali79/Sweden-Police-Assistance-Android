package com.alivakili.sweden_police.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alivakili.sweden_police.model.EventModel
import com.alivakili.sweden_police.databinding.EventItemListBinding
import java.text.SimpleDateFormat
import kotlin.math.abs
import java.util.concurrent.TimeUnit

class EventsRecyclerViewAdapter(
    private val items: EventModel?,
    private val onClicked: (EventModel.EventModelItem?) -> Unit,
    private val context: Context,
) : RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            EventsViewHolder {
        return EventsViewHolder.create(parent, onClicked, context)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = items?.get(position)
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class EventsViewHolder(
        private val binding: EventItemListBinding,
        private val onClicked: (EventModel.EventModelItem?) -> Unit,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel.EventModelItem?) {
            binding.apply {
                name.text = event?.name?.substringAfter(", ")?.trim()?.substringAfter(", ")
                location.text = event?.location?.name
                summery.text = event?.summary
                submitionTime.text = event?.datetime?.let { getTimeDifference(it) }
                root.setOnClickListener(View.OnClickListener {
                    onClicked(event)
                })

            }
        }

        fun getTimeDifference(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
            val currentTime = System.currentTimeMillis()
            val timestampDate = dateFormat.parse(timestamp)
            val difference = currentTime - timestampDate.time

            val minutes = TimeUnit.MILLISECONDS.toMinutes(difference)
            val hours = TimeUnit.MILLISECONDS.toHours(difference)
            val days = TimeUnit.MILLISECONDS.toDays(difference)
            val minutesAgo = abs(minutes % 60).toInt()
            val hoursAgo = abs(hours % 24).toInt()
            val daysAgo = abs(days % 30).toInt()
            if (daysAgo == 0)
                if (hoursAgo == 0)
                    return "$minutesAgo minutes ago"
                else if (minutesAgo == 0)
                    return "$hoursAgo hours ago"
                else
                    return "$hoursAgo hours, $minutesAgo minutes ago"
            else
                if (hoursAgo == 0)
                    return "$daysAgo days, $minutesAgo minutes ago"
                else if (minutesAgo == 0)
                    return "$daysAgo days, $hoursAgo hours ago"
                else
                    return "$daysAgo days, $hoursAgo hours, $minutesAgo minutes ago"
        }


        companion object {
            fun create(
                parent: ViewGroup,
                onClicked: (EventModel.EventModelItem?) -> Unit,
                context: Context
            ): EventsViewHolder {
                val binding = EventItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EventsViewHolder(
                    binding = binding,
                    onClicked = onClicked,
                    context = context
                )
            }
        }
    }


}
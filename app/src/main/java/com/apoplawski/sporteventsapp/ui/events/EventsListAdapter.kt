package com.apoplawski.sporteventsapp.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apoplawski.sporteventsapp.R
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.internal.JavaTimeService
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_event.view.*

class EventsListAdapter (
    private var eventsList : MutableList<Event>,
    private val clickListener : (Event, View) -> Unit
) : RecyclerView.Adapter<EventsListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventsList[position], clickListener, holder)
    }

    fun setItems(newSchedule: List<Event>){
        val oldList = eventsList.toList()
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ScheduleItemDiffCallback(oldList, newSchedule)
        )
        eventsList.clear()
        eventsList.addAll(newSchedule)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind (event: Event, clickListener: (Event, View) -> Unit, holder : ViewHolder){
            itemView.setOnClickListener { clickListener(event, itemView) }
            itemView.eventTitle_textView.text = event.title
            itemView.eventSubtitle_textView.text = event.subtitle
            itemView.eventDate_textView.text = JavaTimeService.processEventDate(event.date)
            Glide.with(holder.itemView.context)
                .load(event.imageUrl)
                .into(itemView.eventThumbnail_imageView)
        }
    }

    class ScheduleItemDiffCallback(
        private var oldScheduleList : List<Event>,
        private var newScheduleList : List<Event>
    ) : DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldScheduleList[oldItemPosition].id == newScheduleList[newItemPosition].id)
        }

        override fun getOldListSize(): Int {
            return oldScheduleList.size
        }

        override fun getNewListSize(): Int {
            return newScheduleList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldScheduleList[oldItemPosition] == (newScheduleList[newItemPosition]))
        }

    }

}
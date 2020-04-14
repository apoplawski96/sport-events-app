package com.apoplawski.sporteventsapp.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apoplawski.sporteventsapp.R
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.apoplawski.sporteventsapp.internal.Change
import com.apoplawski.sporteventsapp.internal.JavaTimeService
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleAdapter (
    private var scheduleList : MutableList<ScheduleItem>,
    private val clickListener : (ScheduleItem, View) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scheduleList[position], clickListener, holder)
    }

    fun setItems(newSchedule: List<ScheduleItem>){
        val oldList = scheduleList.toList()
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ScheduleItemDiffCallback(oldList, newSchedule)
        )
        scheduleList.clear()
        scheduleList.addAll(newSchedule)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind (scheduleItem: ScheduleItem, clickListener: (ScheduleItem, View) -> Unit, holder : ViewHolder){
            itemView.setOnClickListener { clickListener(scheduleItem, itemView) }
            itemView.scheduleItemTitle_textView.text = scheduleItem.title
            itemView.scheduleItemSubtitle_textView.text = scheduleItem.subtitle
            itemView.scheduleItemDate_textView.text = JavaTimeService.processScheduleDate(scheduleItem.date)
            Glide.with(holder.itemView.context)
                .load(scheduleItem.imageUrl)
                .into(itemView.scheduleThumbnail_imageView)
        }
    }

    class ScheduleItemDiffCallback(
        private var oldScheduleList : List<ScheduleItem>,
        private var newScheduleList : List<ScheduleItem>
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

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldScheduleList[oldItemPosition]
            val newItem = newScheduleList[newItemPosition]

            return Change(oldItem, newItem)
        }

    }

}
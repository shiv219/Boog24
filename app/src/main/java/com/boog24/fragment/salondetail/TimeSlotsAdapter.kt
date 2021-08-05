package com.boog24.fragment.salondetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.boog24.R
import com.boog24.databinding.ListTimeSlotBinding
import com.boog24.modals.getSaloonDetail.SaloonData


class TimeSlotsAdapter(private val timeSlots: List<SaloonData.SalonTimeSlot>) : RecyclerView.Adapter<TimeSlotsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.list_time_slot, parent, false
        )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(timeSlots[position])

    }


    inner class MyViewHolder(val item: ListTimeSlotBinding) : RecyclerView.ViewHolder(item.root) {
        @SuppressLint("SetTextI18n")
        fun bind(salonTimeSlot: SaloonData.SalonTimeSlot) {

            item.tvDay.text = salonTimeSlot.day
            if (salonTimeSlot.timeSlots.size == 0) {
                val lparams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                val tv = TextView(item.root.context)
                lparams.topMargin = 8
                tv.layoutParams = lparams
                tv.text = item.root.context.getString(R.string.closed)
                tv.setTextColor(item.root.context.resources.getColor(R.color.black))
                item.llTime.addView(tv)

            }
            salonTimeSlot.timeSlots.forEach {
                val lparams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                val tv = TextView(item.root.context)
                lparams.topMargin = 8
                tv.layoutParams = lparams
                tv.text = "${it.fromTime} - ${it.toTime}"
                tv.setTextColor(item.root.context.resources.getColor(R.color.black))
                item.llTime.addView(tv)
            }
        }
    }

    override fun getItemCount(): Int {
        return timeSlots.size
    }
}
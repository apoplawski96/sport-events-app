package com.apoplawski.sporteventsapp.ui.schedule

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.apoplawski.sporteventsapp.R
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.apoplawski.sporteventsapp.ui.common.MainActivity
import kotlinx.android.synthetic.main.schedule_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import com.apoplawski.sporteventsapp.internal.JavaTimeService


class ScheduleFragment : Fragment(), KodeinAware {

    private val TAG = "ScheduleFragment"

    override val kodein by closestKodein()
    private val viewModelFactory : ScheduleViewModelFactory by instance()
    private lateinit var viewModel: ScheduleViewModel
    private var adapter : ScheduleAdapter? = null

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.schedule_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)
        fetchUI()
    }

    private fun fetchUI() {
        showProgress()
        setupScheduleRecyclerView()
        fetchSchedule()
    }

    private fun setupScheduleRecyclerView(){
        schedule_recyclerView.layoutManager = LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)
        adapter = ScheduleAdapter(mutableListOf()) { event: ScheduleItem, view : View -> eventItemClicked(event, view) }
        schedule_recyclerView.adapter = adapter
    }

    private fun fetchSchedule(){
        viewModel.observeOnSchedule(viewLifecycleOwner, Observer {observedSchedule ->
            if (observedSchedule == null) return@Observer
            adapter!!.setItems(observedSchedule)
            hideProgress()
        })
    }

    private fun eventItemClicked(scheduleItem : ScheduleItem, view : View){ }

    private fun showProgress(){
        progressBar.visibility = View.VISIBLE
        schedule_recyclerView.visibility = View.GONE
    }

    private fun hideProgress(){
        progressBar.visibility = View.GONE
        schedule_recyclerView.visibility = View.VISIBLE
    }
}

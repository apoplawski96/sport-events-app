package com.apoplawski.sporteventsapp.ui.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apoplawski.sporteventsapp.R
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.apoplawski.sporteventsapp.internal.JavaTimeService
import com.apoplawski.sporteventsapp.ui.common.BaseFragment
import com.apoplawski.sporteventsapp.ui.common.MainActivity
import com.apoplawski.sporteventsapp.ui.playback.EventPlaybackActivity
import com.apoplawski.sporteventsapp.ui.schedule.ScheduleAdapter
import com.apoplawski.sporteventsapp.ui.schedule.ScheduleViewModel
import com.apoplawski.sporteventsapp.ui.schedule.ScheduleViewModelFactory
import kotlinx.android.synthetic.main.events_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class EventsFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory : ScheduleViewModelFactory by instance()
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var adapter : ScheduleAdapter

    companion object {
        fun newInstance() = EventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)
        fetchUI()
    }

    private fun fetchUI() {
        showProgress()
        setupEventsRecyclerView()
        fetchEvents()
    }

    private fun setupEventsRecyclerView(){
        events_recyclerView.layoutManager = LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)
        adapter = ScheduleAdapter(mutableListOf()) { event: ScheduleItem, view : View -> eventItemClicked(event, view) }
        events_recyclerView.adapter = adapter
    }

    private fun fetchEvents(){
        viewModel.observeOnSchedule(viewLifecycleOwner, Observer { observedEvents ->
            if (observedEvents == null) return@Observer
            adapter.setItems(observedEvents)
            hideProgress()
        })
    }

    private fun eventItemClicked(event : ScheduleItem, view : View){
        val intent = Intent(activity, EventPlaybackActivity::class.java)
        startActivity(intent)
    }

    private fun showProgress(){
        progressBar.visibility = View.VISIBLE
        events_recyclerView.visibility = View.GONE
    }

    private fun hideProgress(){
        progressBar.visibility = View.GONE
        events_recyclerView.visibility = View.VISIBLE
    }
}

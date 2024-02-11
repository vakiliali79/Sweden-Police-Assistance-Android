package com.alivakili.sweden_police.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alivakili.sweden_police.model.EventModel
import com.alivakili.sweden_police.adapter.EventsRecyclerViewAdapter
import com.alivakili.sweden_police.api.EventsState
import com.alivakili.sweden_police.viewmodel.EventsViewModel
import com.alivakili.sweden_police.databinding.FragmentIncidentsBinding
import kotlinx.coroutines.launch

class IncidentsFragment : Fragment() {
    private var _binding: FragmentIncidentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIncidentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeState()

    }


    private val viewModel: EventsViewModel by viewModels{
        EventsViewModel.factory()
    }

    private fun observeState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect{
                        state->
                    when(state){
                        EventsState.Loading -> showProgressBar()
                        is EventsState.Success -> {
                            showEvents(state.events)
                        }

                        EventsState.Failure -> showErrorMessage()
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun showEvents(events: EventModel?){
        hideProgressBar()

        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            adapter= EventsRecyclerViewAdapter(items = events, onClicked = ::showEvent, context)
            layoutManager= LinearLayoutManager(context)
            setHasFixedSize(true)
            visibility= View.VISIBLE
        }
    }

    private fun showEvent(event: EventModel.EventModelItem?){
        openBrowser(event?.url)
    }
    private fun openBrowser(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://polisen.se$url"))
        startActivity(intent)
    }
    private fun showErrorMessage(){
        Toast.makeText(requireContext(), "Unable to retrieve news!", Toast.LENGTH_LONG).show()
    }
    private fun showProgressBar(){
        binding.progressBar.visibility= View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility= View.GONE
    }

}
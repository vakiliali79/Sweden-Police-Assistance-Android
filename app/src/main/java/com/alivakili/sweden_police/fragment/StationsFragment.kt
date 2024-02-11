package com.alivakili.sweden_police.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alivakili.sweden_police.R
import com.alivakili.sweden_police.api.StationState
import com.alivakili.sweden_police.viewmodel.StationViewModel
import com.alivakili.sweden_police.model.StationsModel
import com.alivakili.sweden_police.adapter.StationsRecyclerViewAdapter
import com.alivakili.sweden_police.databinding.FragmentStationsBinding
import kotlinx.coroutines.launch

class StationsFragment : Fragment() {
    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeState()

    }


    private val viewModel: StationViewModel by viewModels{
        StationViewModel.factory()
    }

    private fun observeState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect{
                        state->
                    when(state){
                        StationState.Loading -> showProgressBar()
                        is StationState.Success -> {
                            showStations(state.stations)
                        }

                        StationState.Failure -> showErrorMessage()
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun showStations(stations: StationsModel?){
        hideProgressBar()

        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            adapter=
                StationsRecyclerViewAdapter(items = stations, onClicked = ::showStation, context)
            layoutManager= LinearLayoutManager(context)
            setHasFixedSize(true)
            visibility= View.VISIBLE
        }
    }

    private fun showStation(station: StationsModel.StationsModelItem?){
        val fragment = MapFragment.newInstance(station)
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null) // Add the transaction to the back stack
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Set an animation
        transaction.replace(R.id.container, fragment)
        transaction.commit()
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
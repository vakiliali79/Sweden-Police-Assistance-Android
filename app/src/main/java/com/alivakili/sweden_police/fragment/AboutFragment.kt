package com.alivakili.sweden_police.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alivakili.sweden_police.R
import com.alivakili.sweden_police.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        properLayout()

    }

    private fun properLayout() {
        binding.incidents.setOnClickListener(View.OnClickListener {
            setCurrFragment(IncidentsFragment())
        })
        binding.source.setOnClickListener(View.OnClickListener {
            openBrowser("https://polisen.se/")
        })
        binding.services.setOnClickListener(View.OnClickListener {
            openBrowser("https://polisen.se/en/victims-of-crime/making-a-report/")
        })
        binding.rules.setOnClickListener(View.OnClickListener {
            openBrowser("https://polisen.se/om-polisen/om-webbplatsen/oppna-data/")
        })
    }
    private fun setCurrFragment(fragment : Fragment){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            commit()
        }

    }
    private fun openBrowser(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

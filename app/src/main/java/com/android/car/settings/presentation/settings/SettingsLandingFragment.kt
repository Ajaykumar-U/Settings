package com.android.car.settings.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.car.settings.R
import com.android.car.settings.databinding.FragmentSettingsLandingBinding
import com.android.car.settings.domain.model.SettingsCategory
import com.android.car.settings.presentation.settings.adapter.SettingsRecyclerViewAdapter

class SettingsLandingFragment : Fragment(), SettingsRecyclerViewAdapter.OnClickThumbListener {

    private var _binding: FragmentSettingsLandingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SettingsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val settingsList = listOf(
            SettingsCategory(R.drawable.ico_system_settings, "General"),
            SettingsCategory(R.drawable.ico_media_settings, "Media"),
            SettingsCategory(R.drawable.ico_connection_settings, "Connectivity"),
            SettingsCategory(R.drawable.ico_my_space, "My Space"),
            SettingsCategory(R.drawable.ico_notification_settings, "Notification"),
            SettingsCategory(R.drawable.ico_ambient_ligthing, "Ambient Light"),
            SettingsCategory(R.drawable.ico_alexa_settings, "Amazon Alexa")
        )

        adapter = SettingsRecyclerViewAdapter(this)
        adapter.setList(settingsList)

        binding.settingsGridRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.settingsGridRecyclerView.adapter = adapter
    }

    override fun onThumbnailClick(item: SettingsCategory) {
        when (item.title) {
            "General" -> findNavController().navigate(R.id.action_settingsLanding_to_general)
            "Media" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_media)
            "Connectivity" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_connectivity)
            "My Space" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_mySpace)
            "Notification" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_notification)
            "Ambient Light" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_ambientLight)
            "Amazon Alexa" -> findNavController().navigate(R.id.action_settingsLandingFragment_to_alexa)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
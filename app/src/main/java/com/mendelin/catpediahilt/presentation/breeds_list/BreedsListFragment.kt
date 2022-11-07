package com.mendelin.catpediahilt.presentation.breeds_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.data.local.entity.toBreed
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import com.mendelin.catpediahilt.data.remote.model.toBreed
import com.mendelin.catpediahilt.databinding.FragmentBreedsListBinding
import com.mendelin.catpediahilt.presentation.main.BreedCallback
import com.mendelin.catpediahilt.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedsListFragment : Fragment() {

    private var binding: FragmentBreedsListBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var breedsAdapter: BreedsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupUi() {
        breedsAdapter = BreedsListAdapter(getBreedInfoListener())

        binding?.recyclerBreeds?.apply {
            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = null
            isNestedScrollingEnabled = true
            setHasFixedSize(true)

        }

        binding?.swipeList?.setOnRefreshListener {
            mainViewModel.fetchBreedsList()
            binding?.swipeList?.isRefreshing = false
        }

        mainViewModel.fetchOfflineBreedsList()

        if (mainViewModel.breedsList.value.isEmpty()) {
            mainViewModel.fetchBreedsList()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.offlineBreedsList.collectLatest {
                    if (it.isNotEmpty()) {
                        breedsAdapter.setList(it.map(BreedEntity::toBreed))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.breedsList.collectLatest {
                    val breeds = it.map(BreedsListModel::toBreed)
                    breedsAdapter.setList(breeds)

                    breeds.forEach(mainViewModel::createOfflineBreedsList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isLoading.collectLatest {
                    binding?.progressBar?.visibility = if (it) View.VISIBLE else View.INVISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isFailed.collectLatest { (hasFailed, message) ->
                    if (hasFailed) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
                            .show()
                        mainViewModel.fetchOfflineBreedsList()
                    }
                }
            }
        }
    }

    private fun getBreedInfoListener(): BreedCallback =
        BreedCallback {
            if (it.isNotEmpty()) {
                val action = BreedsListFragmentDirections.actionFirstFragmentToSecondFragment(it)
                findNavController().navigate(action)
            }
        }
}
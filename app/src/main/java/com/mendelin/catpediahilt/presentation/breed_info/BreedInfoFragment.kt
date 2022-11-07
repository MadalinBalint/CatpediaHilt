package com.mendelin.catpediahilt.presentation.breed_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpediahilt.data.local.entity.toBreedDetails
import com.mendelin.catpediahilt.data.remote.model.toBreedDetails
import com.mendelin.catpediahilt.databinding.FragmentBreedInfoBinding
import com.mendelin.catpediahilt.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedInfoFragment : Fragment() {

    private var binding: FragmentBreedInfoBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: BreedInfoFragmentArgs by navArgs()
    private lateinit var catsAdapter: CatImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedInfoBinding.inflate(inflater, container, false)
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
        catsAdapter = CatImageAdapter()

        binding?.recyclerCats?.apply {
            adapter = catsAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            isNestedScrollingEnabled = true
            //setHasFixedSize(true)
        }

        mainViewModel.fetchOfflineBreedInfo(args.breedId)

        mainViewModel.fetchBreedInfo(args.breedId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.offlineBreedDetails.collectLatest { details ->
                    details?.toBreedDetails()?.let {
                        binding?.info = it

                        Log.d("TAG", it.toString())

                        catsAdapter.setList(it.imageUrls)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.breedInfo.collectLatest { details ->
                    val breedDetails = details.toBreedDetails()
                    binding?.info = breedDetails

                    mainViewModel.createOfflineBreedsInfo(breedDetails)

                    Log.d("TAG", breedDetails.toString())

                    catsAdapter.setList(breedDetails.imageUrls)
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
                    }
                }
            }
        }
    }
}
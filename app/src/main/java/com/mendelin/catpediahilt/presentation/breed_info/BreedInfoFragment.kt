package com.mendelin.catpediahilt.presentation.breed_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpediahilt.databinding.FragmentBreedInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedInfoFragment : Fragment() {

    private var binding: FragmentBreedInfoBinding? = null
    private val viewModel: BreedInfoViewModel by viewModels()
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
            itemAnimator = null
            isNestedScrollingEnabled = true
        }

        viewModel.fetchOfflineBreedInfo(args.breedId)

        viewModel.fetchBreedInfo(args.breedId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    binding?.progressBar?.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE
                    val (hasFailed, message) = state.isFailed

                    if (hasFailed) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
                            .show()
                        viewModel.fetchOfflineBreedInfo(args.breedId)
                    } else {
                        val info = state.breedInfo
                        if (info != null) {
                            binding?.info = info

                            Log.d("TAG", info.toString())
                            catsAdapter.setList(info.imageUrls)

                            if (!state.isOffline) {
                                viewModel.createOfflineBreedsInfo(info)
                            }
                        }
                    }
                }
            }
        }
    }
}
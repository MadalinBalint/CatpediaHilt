package com.mendelin.catpediahilt.presentation.breeds_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mendelin.catpediahilt.databinding.FragmentBreedsListBinding
import com.mendelin.catpediahilt.presentation.main.BreedCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedsListFragment : Fragment() {

    private var binding: FragmentBreedsListBinding? = null
    private val viewModel: BreedsViewModel by viewModels()
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
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            itemAnimator = null
            isNestedScrollingEnabled = true
        }

        binding?.swipeList?.setOnRefreshListener {
            viewModel.fetchOfflineBreedsList()
            viewModel.fetchBreedsList()
            binding?.swipeList?.isRefreshing = false
        }

        viewModel.fetchOfflineBreedsList()

        if (viewModel.viewState.value.breeds.isEmpty()) {
            viewModel.fetchBreedsList()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    binding?.progressBar?.visibility =
                        if (state.isLoading) View.VISIBLE else View.INVISIBLE
                    val (hasFailed, message) = state.isFailed

                    if (hasFailed) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
                            .show()
                        viewModel.fetchOfflineBreedsList()
                    } else {
                        val breeds = state.breeds
                        if (breeds.isNotEmpty()) {
                            breedsAdapter.setList(breeds)

                            if (!state.isOffline) {
                                breeds.forEach(viewModel::createOfflineBreedsList)
                            }
                        }
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
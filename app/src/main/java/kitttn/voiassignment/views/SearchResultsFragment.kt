package kitttn.voiassignment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kitttn.api.entities.Artist
import kitttn.voiassignment.R
import kitttn.voiassignment.extensions.component
import kitttn.voiassignment.extensions.createViewModel
import kitttn.voiassignment.extensions.observe
import kitttn.voiassignment.viewmodel.SearchViewModel
import kitttn.voiassignment.views.databinding.ArtistAdapter
import kotlinx.android.synthetic.main.fragment_search_results.*

class SearchResultsFragment : Fragment() {
    private val viewModel by lazy {
        createViewModel(this) { component.searchViewModel }
    }

    private val artists = mutableListOf<Artist>()
    private val adapter by lazy { ArtistAdapter(artists) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchQuery = arguments?.getString("query") ?: return findNavController().navigateUp().let {}

        searchResultsRV.layoutManager = LinearLayoutManager(context)
        searchResultsRV.adapter = adapter

        viewModel.searchResultsScreen.observe(this) {
            searchResultsLoading.visibility = if (it is SearchViewModel.SearchResultsState.Loading) View.VISIBLE else View.GONE

            when (it) {
                is SearchViewModel.SearchResultsState.Initial -> viewModel.requestArtists(searchQuery)
                is SearchViewModel.SearchResultsState.Loaded -> updateArtists(it.items)
                is SearchViewModel.SearchResultsState.Error -> Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateArtists(newList: List<Artist>) {
        artists.clear()
        artists.addAll(newList)
        adapter.notifyDataSetChanged()
    }
}
package kitttn.voiassignment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kitttn.voiassignment.R
import kitttn.voiassignment.extensions.component
import kitttn.voiassignment.extensions.createViewModel
import kitttn.voiassignment.viewmodel.SearchViewModel

class SearchResultsFragment : Fragment() {
    private val viewModel by lazy {
        createViewModel(activity) { component.searchViewModel }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchQuery = arguments?.getString("query") ?: return findNavController().navigateUp().let {}
        viewModel.requestArtists(searchQuery)
    }
}
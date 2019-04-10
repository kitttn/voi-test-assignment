package kitttn.voiassignment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kitttn.voiassignment.R
import kitttn.voiassignment.extensions.*
import kitttn.voiassignment.viewmodel.MainScreenState
import kitttn.voiassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private val viewModel by lazy {
        createViewModel(activity) { component.mainViewModel }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(this) {
            when (it) {
                is MainScreenState.Loading -> showLoading(true)
                is MainScreenState.SearchReady -> {
                    searchInputTxt.setText(it.searchQuery)
                    showLoading(false)
                }
                is MainScreenState.Error -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.error.message)
                        .show()

                    showLoading(false)
                }
            }
        }

        searchStartBtn.setOnClickListener exit@{
            hideKeyboard()

            val query = searchInputTxt.text.toString()
            if (query.isEmpty())
                return@exit

            val bundle = Bundle().apply { putString("query", query) }
            findNavController().navigate(R.id.action_searchFragment_to_searchResultsFragment, bundle)
        }
    }

    private fun showLoading(loading: Boolean) {
        searchProgressIndicator.visibility = if (loading) View.VISIBLE else View.GONE
        searchStartBtn.isEnabled = !loading
    }
}
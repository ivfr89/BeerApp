package com.developer.ivan.beerapp.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.ivan.beerapp.core.EventObserver
import com.developer.ivan.beerapp.core.application
import com.developer.ivan.beerapp.databinding.FragmentBeerListBinding
import com.developer.ivan.beerapp.ui.adapters.BeerListAdapter
import com.developer.ivan.beerapp.ui.main.fragments.BeerListViewModel.NavigationEvent
import com.developer.ivan.beerapp.ui.main.models.UIBeer
import com.developer.ivan.beerapp.ui.main.di.BeerListSubComponent
import com.developer.ivan.beerapp.ui.pagination.BeerPaginationListener
import javax.inject.Inject

class BeerListFragment : Fragment() {


    @Inject
    lateinit var mViewModel: BeerListViewModel

    private lateinit var mSubcomponent: BeerListSubComponent

    private var state: Parcelable? = null

    private lateinit var binding: FragmentBeerListBinding
    private lateinit var mBeerAdapter: BeerListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mBeerPagination: BeerPaginationListener


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mSubcomponent = application.component.beerListSubcomponent.create(state)
        mSubcomponent.inject(this)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentBeerListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureToolbar()
        configureUI()
        configureEvents()
        configureStates()



    }

    override fun onResume() {
        super.onResume()
        mViewModel.getBeers()

    }

    override fun onPause() {
        super.onPause()
        mViewModel.state = mLayoutManager.onSaveInstanceState()
    }

    private fun configureStates() {

        mViewModel.beersStateListData.observe(viewLifecycleOwner, Observer { state ->

            when (state) {
                is BeerListViewModel.BeerListState.Error -> Log.e("Error", state.toString())
                is BeerListViewModel.BeerListState.ShowItems -> mBeerAdapter.submitList(state.beerList)
                is BeerListViewModel.BeerListState.IsLoading -> with(binding) {
                    progressBar.isVisible = state.isLoading
                    mBeerPagination.isLoading = state.isLoading
                }
                is BeerListViewModel.BeerListState.IsLastPage -> mBeerPagination.isLastPage =
                    state.isLastPage
            }
        })
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(
            activity as AppCompatActivity,
            findNavController()
        )
    }

    private fun configureEvents() {

        mViewModel.navigation.observe(viewLifecycleOwner, EventObserver { event ->

            when (event) {
                is NavigationEvent.ToDetail -> {
                    val extras = FragmentNavigatorExtras(event.title to event.arg.id.toString())
                    val action =
                        BeerListFragmentDirections.actionFromListToDetail(
                            event.arg
                        )

                    findNavController().navigate(action,extras)
                }
            }
        })

    }


    private fun configureUI() {
        with(binding) {
            mBeerAdapter = BeerListAdapter(::onClickOnItem)
            mLayoutManager = LinearLayoutManager(context)
            mBeerPagination = BeerPaginationListener(
                mLayoutManager,
                isLastPage = false,
                isLoading = false,
                callback = ::onLoadMore
            )

            rcvBeerList.adapter = mBeerAdapter
            rcvBeerList.layoutManager = mLayoutManager

            rcvBeerList.addOnScrollListener(mBeerPagination)
            mViewModel.state?.let { mLayoutManager.onRestoreInstanceState(it) }

        }
    }

    private fun onLoadMore() {
        mViewModel.getBeers()
    }

    private fun onClickOnItem(uiBeer: UIBeer, title: TextView) {
        mViewModel.navigateTo(NavigationEvent.ToDetail(uiBeer,title))
    }


}
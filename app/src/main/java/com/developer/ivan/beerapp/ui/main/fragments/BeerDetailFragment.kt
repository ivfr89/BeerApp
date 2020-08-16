package com.developer.ivan.beerapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionInflater
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.core.application
import com.developer.ivan.beerapp.core.formatDecimals
import com.developer.ivan.beerapp.core.loadImageOrHide
import com.developer.ivan.beerapp.core.setNotNullText
import com.developer.ivan.beerapp.databinding.FragmentBeerDetailBinding
import com.developer.ivan.beerapp.ui.main.UIBeer
import com.developer.ivan.beerapp.ui.main.di.BeerDetailSubComponent
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject

class BeerDetailFragment : Fragment() {

    private lateinit var binding: FragmentBeerDetailBinding

    @Inject
    lateinit var mViewModel: BeerDetailViewModel

    lateinit var mSubcomponent: BeerDetailSubComponent

    private val args: BeerDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        mSubcomponent = application.component.beerDetailSubcomponent.create()
        mSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        configureAppBarScroll()
        configureListeners()
        configureObservers()

        mViewModel.getBeer(args.uibeer.id)

    }

    private fun configureToolbar() {
        binding.toolbar.apply {
            transitionName = args.uibeer.id.toString()
            title = args.uibeer.name
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        NavigationUI.setupActionBarWithNavController(
            activity as AppCompatActivity,
            findNavController()
        )


    }

    private fun configureListeners() {

        binding.btnAvailable.setOnClickListener {
            mViewModel.switchAvailability()
        }
    }

    private fun configureObservers() {

        mViewModel.beersStateData.observe(viewLifecycleOwner, Observer { beerState ->

            when(beerState){
                is BeerDetailViewModel.BeerListState.ShowItem -> configureUI(beerState.item)
            }


        })
    }

    private fun configureUI(item: UIBeer) {
        with(binding){
            txtName.text = item.name
            txtDescription.text = item.description
            txtAlcohol.setNotNullText(item.abv){ context?.getString(R.string.alcohol_by_volume, it.formatDecimals(1)) }
            txtBitterness.setNotNullText(item.ibu) { context?.getString(R.string.bitterness, it.formatDecimals(1)) }
            txtFoodPairing.setNotNullText(item.food_pairing) { context?.getString(R.string.food_pairing, it.joinToString(", ")) }
            imgBeer.loadImageOrHide(item.image_url)

            if(!item.isAvailable){
                btnAvailable.text = context?.getString(R.string.set_available)
                txtAvailability.isVisible = true
                txtAvailability.apply {
                    text = context?.getString(R.string.not_available)
                    setBackgroundColor(context.getColor(R.color.grayLight))
                }
            }else{
                btnAvailable.text = context?.getString(R.string.set_not_available)
                txtAvailability.isVisible = false
            }


        }
    }

    private fun configureAppBarScroll() {

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            binding.motionLayout.progress = seekPosition
            binding.motionBody.progress = seekPosition
        })
    }


}
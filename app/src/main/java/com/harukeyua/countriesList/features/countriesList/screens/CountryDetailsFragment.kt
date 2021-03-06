package com.harukeyua.countriesList.features.countriesList.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.harukeyua.countriesList.databinding.FragmentCountryDetailsBinding
import com.harukeyua.countriesList.features.countriesList.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private val viewModel by activityViewModels<CountriesViewModel>()
    private var _screenBinding: FragmentCountryDetailsBinding? = null
    private val screenBinding get() = _screenBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _screenBinding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return screenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch() {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCountryInfoFlow.collectLatest { info ->
                    with(screenBinding) {
                        makeVisibleWithAnimation(root)
                        root.isInvisible = false
                        countryName.text = info.name
                        continentInfo.text = info.continent
                        capitalInfo.text = info.capital
                        codeInfo.text = info.code
                        phoneInfo.text = info.phone
                        currencyInfo.text = info.currency
                        languageInfo.text = info.language
                        flagImage.isInvisible = info.flagResource == null
                        flagEmoji.isInvisible = info.flagResource != null
                        info.flagResource?.also {
                            flagImage.setImageResource(it)
                        } ?: flagImage.setImageDrawable(null)
                        flagEmoji.text = info.emojiCode

                    }
                }
            }
        }
    }

    private fun makeVisibleWithAnimation(view: View) {
        if (view.isInvisible) {
            view.alpha = 0f
            view.isInvisible = false
            view.animate().alpha(1f)
                .setInterpolator(FastOutSlowInInterpolator())
                .setDuration(500)
                .start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _screenBinding = null
    }
}
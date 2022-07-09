package com.harukeyua.countriesList.features.countriesList.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.harukeyua.countriesList.R
import com.harukeyua.countriesList.databinding.CountryListItemBinding
import com.harukeyua.countriesList.databinding.FragmentCountriesListBinding
import com.harukeyua.countriesList.features.countriesList.list.CountriesListItemDecoration
import com.harukeyua.countriesList.features.countriesList.CountriesViewModel
import com.harukeyua.countriesList.features.countriesList.model.CountryItemModel
import com.harukeyua.countriesList.utils.ItemModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    private val viewModel by activityViewModels<CountriesViewModel>()

    private var _screenBinding: FragmentCountriesListBinding? = null
    private val screenBinding get() = _screenBinding!!

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(listItemDiffCallback(), countryAdapterDelegate {
            viewModel.selectCountry(it.code)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _screenBinding = FragmentCountriesListBinding.inflate(inflater, container, false)
        return screenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        screenBinding.countriesList.adapter = adapter
        screenBinding.countriesList.addItemDecoration(CountriesListItemDecoration())

        viewLifecycleOwner.lifecycleScope.launch() {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countriesListFlow.collectLatest {
                    adapter.setItems(it)
                }
            }
        }

        screenBinding.searchEditText.doAfterTextChanged {
            viewModel.performSearch(it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _screenBinding = null
    }

    private fun countryAdapterDelegate(itemClickListener: (CountryItemModel) -> Unit) =
        adapterDelegateViewBinding<CountryItemModel, ItemModel, CountryListItemBinding>(
            { layoutInflater, parent ->
                CountryListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            }
        ) {
            binding.root.setOnClickListener {
                itemClickListener(item)
            }
            bind {
                with(binding) {
                    flagImage.isVisible = item.flagResource != null
                    flagEmoji.isVisible = item.flagResource == null
                    item.flagResource?.also {
                        flagImage.setImageResource(it)
                    } ?: flagImage.setImageDrawable(null)
                    flagEmoji.text = item.emojiCode
                    name.text = item.name
                    root.setCardBackgroundColor(
                        if (item.selected)
                            getColor(R.color.seashell)
                        else
                            getColor(R.color.white)
                    )
                }
            }
        }

    private fun listItemDiffCallback() = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {
            return if (oldItem is CountryItemModel && newItem is CountryItemModel) oldItem.code == newItem.code else false
        }

        override fun areContentsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {
            return if (oldItem is CountryItemModel && newItem is CountryItemModel) oldItem == newItem else false
        }
    }
}
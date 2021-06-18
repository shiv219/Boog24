package com.boog24.util.countryCode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.boog24.R
import com.boog24.databinding.CountryCodeDialogFragmentBinding
import com.boog24.util.ScreenUtils


class CountryCodeDialog(
        val position: Int,
        private val onSelect: SelectListener
) :
        DialogFragment() {

    lateinit var adapter: CountryCodeAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val mBinding: CountryCodeDialogFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.country_code_dialog_fragment,
                null,
                false
        )
        val layoutParam = mBinding.innerConstraintLayout.layoutParams
        layoutParam.width = (ScreenUtils.getScreenWidth(requireContext()) * 0.90).toInt()
        layoutParam.height = (ScreenUtils.getScreenHeight(requireContext()) * 0.90).toInt()
        mBinding.clParent.layoutParams = layoutParam
        setRecyclerView(mBinding)
        mBinding.ivCancel.setOnClickListener {
            dismiss()
        }
        onSearch(mBinding)

        return mBinding.root
    }

    private fun onSearch(mBinding: CountryCodeDialogFragmentBinding) {

        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })

    }

    private fun getCountryNameList(): ArrayList<Country> {
        val arrCountry = resources.getStringArray(R.array.country_arrays).toCollection(ArrayList())
        val arrCode = resources.getStringArray(R.array.country_code).toCollection(ArrayList())
        val listCountry = ArrayList<Country>()
        arrCountry.forEachIndexed { index, country ->
            try {
                listCountry.add(
                        Country(
                                countryName = country, countryCode = arrCode[index],
                                isSelected = position == index
                        )
                )
            } catch (ex: Exception) {

            }
        }
        return listCountry
    }

    private fun setRecyclerView(mBinding: CountryCodeDialogFragmentBinding) {
        try {
            adapter = CountryCodeAdapter(getCountryNameList()) { country, position ->
                onSelect.onSelect(position, country.countryCode)
                dismiss()
            }
            mBinding.rvCountry.adapter = adapter
        } catch (ex: Exception) {
        }
    }

    companion object {
        const val TAG = "CountryDialogFragment"
    }
}

interface SelectListener {

    fun onSelect(position: Int, str: String)
}


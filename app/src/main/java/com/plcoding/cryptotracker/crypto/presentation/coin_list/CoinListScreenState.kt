package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUI

@Immutable
data class CoinListScreenState(
    val isLoading:Boolean=false,
    val coins:List<CoinUI> = emptyList(),
    val selectedCoin:CoinUI?=null
)

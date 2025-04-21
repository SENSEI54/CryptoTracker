package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinsDataSource: CoinDataSource
): ViewModel() {

    private val _state= MutableStateFlow(CoinListScreenState())
    val state=_state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListScreenState()
        )

    fun onAction(action: CoinListAction){
        when(action){
            is CoinListAction.OnCoinClick -> {
                TODO()
            }
        }
    }

    private fun loadCoins(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            coinsDataSource
                .getCoins()
                .onSuccess { coins->
                    _state.update{
                        it.copy(
                            isLoading = false,
                            coins= coins.map {coin->
                                coin.toCoinUI()
                            }
                        )
                    }

                }
                .onError { error->
                    _state.update{
                        it.copy(
                            isLoading = false
                        )
                    }

                }
        }
    }
}
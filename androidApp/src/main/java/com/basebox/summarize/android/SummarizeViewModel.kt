package com.basebox.summarize.android

import AIService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SummarizeViewModel(private val aiService: AIService) : ViewModel() {
    private val _summary = MutableStateFlow("")
    val summary: StateFlow<String> get() = _summary

    fun summarizeText(text: String) {
        viewModelScope.launch {
            _summary.value = aiService.getSummary(text) ?: "Error fetching summary"
        }
    }

    fun clearSummary() {
        _summary.value = ""
    }
}

class SummarizeViewModelFactory(private val aiService: AIService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SummarizeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SummarizeViewModel(aiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
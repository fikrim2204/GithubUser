package rpl1pnp.fikri.githubuser.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rpl1pnp.fikri.githubuser.repository.local.DatabaseRepository
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: DatabaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown class name")
    }
}
package com.example.dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dagger2.adapter.UserAdapter
import com.example.dagger2.databinding.ActivityMainBinding
import com.example.dagger2.utils.UserState
import com.example.dagger2.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: UserViewModel
    private val userAdapter by lazy { UserAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.component.inject(this)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    is UserState.Idle -> Unit
                    is UserState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is UserState.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, it.string, Toast.LENGTH_SHORT).show()
                    }
                    is UserState.Success -> {
                        binding.progressBar.isVisible = false
                        userAdapter.submitList(it.userList)
                    }
                }
            }
        }

    }
}
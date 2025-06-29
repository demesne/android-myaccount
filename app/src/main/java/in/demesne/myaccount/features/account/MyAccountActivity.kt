package `in`.demesne.myaccount.features.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import `in`.demesne.myaccount.databinding.ActivityMyAccountBinding
import `in`.demesne.myaccount.features.login.LoginActivity

@AndroidEntryPoint
class MyAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyAccountBinding
    private val viewModel: MyAccountViewModel by viewModels()
    private lateinit var adapter: AccountListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Setup RecyclerView
        adapter = AccountListAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyAccountActivity)
            adapter = this@MyAccountActivity.adapter
        }

        // Setup logout button
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

        // Setup refresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        // Setup add passkey button
        binding.fabAddPasskey.setOnClickListener {
            viewModel.startPasskeyEnrollment(this)
        }
    }

    private fun observeViewModel() {
        viewModel.accountState.observe(this) { state ->
            binding.swipeRefresh.isRefreshing = false

            when (state) {
                is MyAccountState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is MyAccountState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    adapter.updateData(state.data)
                }
                is MyAccountState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.logoutEvent.observe(this) { shouldLogout ->
            if (shouldLogout) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
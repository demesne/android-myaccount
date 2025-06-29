package `in`.demesne.myaccount.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.demesne.myaccount.features.auth.AuthManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Username and password cannot be empty")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val result = authManager.login(username, password)

            if (result.isSuccess) {
                _loginState.value = LoginState.Success(result.getOrNull() ?: "Login successful")
            } else {
                _loginState.value = LoginState.Error(
                    result.exceptionOrNull()?.message ?: "Login failed"
                )
            }
        }
    }
}
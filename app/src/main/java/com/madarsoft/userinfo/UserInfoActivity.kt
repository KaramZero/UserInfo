package com.madarsoft.userinfo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import com.madarsoft.userinfo.databinding.ActivityUserInfoBinding
import com.madarsoft.userinput.UserInputFragment
import com.madarsoft.userinput.UserInputFragmentDirections
import com.madarsoft.useroutput.UserOutputFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserInputFragment.navigateToOutput = {
            Log.e("TAG", "onCreate: navigateToOutput", )
            Navigation.findNavController(findViewById(R.id.nav_host_fragment_activity))
                .navigate(UserInputFragmentDirections.actionUserInputFragmentToUserOutputFragment())
        }
        UserOutputFragment.navigateBack = {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment_activity)).popBackStack()
        }
    }
}

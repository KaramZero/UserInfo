package com.madarsoft.userinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.madarsoft.core.components.composableWithAnimation
import com.madarsoft.userinfo.ui.theme.UserInfoTheme
import com.madarsoft.userinputv2.UserInputScreen
import com.madarsoft.useroutputv2.UserOutputScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivityV2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.UserInput.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {

        composableWithAnimation(route = NavigationItem.UserInput.route) {
            UserInputScreen { navController.navigate(NavigationItem.UserOutput.route) }

        }
        composableWithAnimation(route = NavigationItem.UserOutput.route) {
            UserOutputScreen { navController.popBackStack() }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun AppNavHostPreview() {
    UserInfoTheme {
        AppNavHost(navController = rememberNavController())
    }
}

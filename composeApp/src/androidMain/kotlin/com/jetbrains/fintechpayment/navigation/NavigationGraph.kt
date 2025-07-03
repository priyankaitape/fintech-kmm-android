package com.jetbrains.fintechpayment.navigation


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jetbrains.fintechpayment.ui.screens.SendPaymentScreenContainer
import com.jetbrains.fintechpayment.ui.screens.TransactionHistoryScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.SendPayment.route,
        modifier = modifier
    ) {

        composable(BottomNavScreen.SendPayment.route) {
            SendPaymentScreenContainer(
                snackbarHostState = snackbarHostState,
                onSuccess = {
                    //handle what to do when the payment is successful
                    //navController.navigate(BottomNavScreen.History.route)
                }
            )
        }

        composable(BottomNavScreen.History.route) {
            TransactionHistoryScreen()
        }
    }
}

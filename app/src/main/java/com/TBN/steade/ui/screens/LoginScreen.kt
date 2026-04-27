package com.TBN.steade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.R
import com.TBN.steade.ui.components.DarkGradientBackground
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

// Shared OutlinedTextField colours used by all forms in the app.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun webTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor          = Color(0xFF111111),
    unfocusedTextColor        = Color(0xFF111111),
    disabledTextColor         = Color(0xFF666666),
    errorTextColor            = Color(0xFF111111),
    focusedContainerColor     = Color.White,
    unfocusedContainerColor   = Color.White,
    disabledContainerColor    = Color(0xFFF5F5F5),
    errorContainerColor       = Color.White,
    focusedBorderColor        = Color(0xFF25408F),
    unfocusedBorderColor      = Color(0xFFDDDDDD),
    errorBorderColor          = Color(0xFFD32F2F),
    focusedLabelColor         = Color(0xFF25408F),
    unfocusedLabelColor       = Color(0xFF888888),
    focusedPlaceholderColor   = Color(0xFFAAAAAA),
    unfocusedPlaceholderColor = Color(0xFFAAAAAA),
    cursorColor               = Color(0xFF25408F),
    errorCursorColor          = Color(0xFFD32F2F),
    focusedTrailingIconColor   = Color(0xFF555555),
    unfocusedTrailingIconColor = Color(0xFF888888),
    errorTrailingIconColor     = Color(0xFFD32F2F)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: SteadEViewModel) {
    var email            by remember { mutableStateOf("") }
    var password         by remember { mutableStateOf("") }
    var passwordVisible  by remember { mutableStateOf(false) }
    var showForgotDialog by remember { mutableStateOf(false) }
    var forgotEmail      by remember { mutableStateOf("") }
    var resetSent        by remember { mutableStateOf(false) }

    val isEmailValid  = remember(email)       { android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    val isForgotValid = remember(forgotEmail) { android.util.Patterns.EMAIL_ADDRESS.matcher(forgotEmail).matches() }

    if (showForgotDialog) {
        AlertDialog(
            onDismissRequest = { showForgotDialog = false; resetSent = false; forgotEmail = "" },
            title = { Text("Reset Password", fontWeight = FontWeight.Bold) },
            text  = {
                Column {
                    if (!resetSent) {
                        Text("Enter your email and we'll send you a reset link.")
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value           = forgotEmail,
                            onValueChange   = { forgotEmail = it },
                            label           = { Text("Email") },
                            isError         = forgotEmail.isNotEmpty() && !isForgotValid,
                            modifier        = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors          = webTextFieldColors()
                        )
                        if (forgotEmail.isNotEmpty() && !isForgotValid) {
                            Text("Invalid email", color = Color.Red, fontSize = 12.sp)
                        }
                    } else {
                        Text("A reset link has been sent to $forgotEmail.")
                    }
                }
            },
            confirmButton = {
                if (!resetSent) {
                    Button(
                        onClick = { if (isForgotValid) resetSent = true },
                        enabled = isForgotValid,
                        colors  = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)
                    ) { Text("Send Link") }
                } else {
                    Button(
                        onClick = { showForgotDialog = false; resetSent = false; forgotEmail = "" },
                        colors  = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)
                    ) { Text("Close") }
                }
            },
            dismissButton = {
                if (!resetSent) TextButton(onClick = { showForgotDialog = false }) { Text("Cancel") }
            }
        )
    }

    DarkGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))

            Image(
                painter            = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier           = Modifier.size(90.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("Sign In", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
            Text(
                "Welcome back! Sign in to your account.",
                color    = Color.White.copy(alpha = 0.75f),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(32.dp))

            Surface(
                shape           = RoundedCornerShape(20.dp),
                color           = Color.White,
                shadowElevation = 8.dp,
                modifier        = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    Text("Email", color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value           = email,
                        onValueChange   = { email = it },
                        placeholder     = { Text("Enter your email address") },
                        isError         = email.isNotEmpty() && !isEmailValid,
                        modifier        = Modifier.fillMaxWidth(),
                        shape           = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine      = true,
                        colors          = webTextFieldColors()
                    )
                    if (email.isNotEmpty() && !isEmailValid) {
                        Text("Invalid email format", color = Color.Red, fontSize = 11.sp)
                    }

                    Spacer(Modifier.height(18.dp))

                    Text("Password", color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value                = password,
                        onValueChange        = { password = it },
                        placeholder          = { Text("Enter your password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                                               else PasswordVisualTransformation(),
                        trailingIcon         = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector        = if (passwordVisible) Icons.Default.VisibilityOff
                                                         else Icons.Default.Visibility,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        modifier             = Modifier.fillMaxWidth(),
                        shape                = RoundedCornerShape(8.dp),
                        keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine           = true,
                        colors               = webTextFieldColors()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showForgotDialog = true }) {
                            Text("Forgot Password?", color = SteadeNavyBlue, fontSize = 13.sp)
                        }
                    }

                    if (viewModel.authError != null) {
                        Spacer(Modifier.height(4.dp))
                        Text(viewModel.authError!!, color = Color.Red, fontSize = 13.sp)
                        Spacer(Modifier.height(8.dp))
                    }

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick  = {
                            viewModel.authError = null
                            viewModel.login(email, password) {
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(Screen.Welcome.route) { inclusive = true }
                                }
                            }
                        },
                        enabled  = isEmailValid && password.isNotEmpty() && !viewModel.authLoading,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape    = RoundedCornerShape(8.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor         = SteadeNavyBlue,
                            contentColor           = Color.White,
                            disabledContainerColor = SteadeNavyBlue.copy(alpha = 0.5f),
                            disabledContentColor   = Color.White.copy(alpha = 0.7f)
                        )
                    ) {
                        if (viewModel.authLoading) {
                            CircularProgressIndicator(
                                color       = Color.White,
                                modifier    = Modifier.size(22.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Sign In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account? ", color = Color(0xFF555555), fontSize = 14.sp)
                        TextButton(
                            onClick        = { navController.navigate(Screen.Register.route) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Sign up",
                                color      = SteadeNavyBlue,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 14.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

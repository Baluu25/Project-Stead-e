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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.R
import com.TBN.steade.ui.components.DarkGradientBackground
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel: SteadEViewModel) {
    var name            by remember { mutableStateOf("") }
    var username        by remember { mutableStateOf("") }
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showTandCDialog  by remember { mutableStateOf(false) }
    var hasScrolledToEnd by remember { mutableStateOf(false) }

    val isValidEmail = remember(email)    { android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    val isPasswordOk = password.length >= 8
    val fieldsReady  = name.isNotBlank() && username.isNotBlank() && isValidEmail && isPasswordOk

    if (showTandCDialog) {
        AlertDialog(
            onDismissRequest = { showTandCDialog = false },
            title = { Text("Terms & Conditions", fontWeight = FontWeight.Bold) },
            text  = {
                val scrollState = rememberScrollState()
                LaunchedEffect(scrollState.value, scrollState.maxValue) {
                    if (scrollState.maxValue > 0 && scrollState.value >= scrollState.maxValue)
                        hasScrolledToEnd = true
                }
                Column {
                    Box(modifier = Modifier.height(220.dp).verticalScroll(scrollState)) {
                        Text(
                            "General Terms and Conditions for Stead-E\n\n" +
                            "1. Acceptance: By using this app you agree to these terms.\n" +
                            "2. Data Usage: We use your data to improve your experience.\n" +
                            "3. Limitations: Some account settings can ONLY be changed on the website.\n" +
                            "4. Privacy: Your data is protected under our privacy policy.\n\n" +
                            "Scroll to the bottom to enable the Accept button.\n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod " +
                            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
                            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
                            "cillum dolore eu fugiat nulla pariatur.",
                            color = Color(0xFF333333)
                        )
                    }
                    if (!hasScrolledToEnd) {
                        Spacer(Modifier.height(6.dp))
                        Text("\u2193 Scroll down to enable Accept", color = Color(0xFFFF6B00), fontSize = 11.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showTandCDialog = false
                        viewModel.authError = null
                        viewModel.register(name.trim(), username.trim(), email.trim(), password) {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                    },
                    enabled = hasScrolledToEnd,
                    colors  = ButtonDefaults.buttonColors(
                        containerColor         = SteadeNavyBlue,
                        disabledContainerColor = Color(0xFFCCCCCC)
                    )
                ) { Text("Accept & Create Account") }
            },
            dismissButton = {
                TextButton(onClick = { showTandCDialog = false }) { Text("Cancel") }
            }
        )
    }

    DarkGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))
            Image(painterResource(R.drawable.logo), "Logo", modifier = Modifier.size(70.dp))
            Spacer(Modifier.height(12.dp))
            Text("Create your account", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(
                "Join Stead-E and start your journey today",
                color     = Color.White.copy(alpha = 0.75f),
                fontSize  = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(28.dp))

            Surface(
                shape           = RoundedCornerShape(20.dp),
                color           = Color.White,
                shadowElevation = 8.dp,
                modifier        = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    if (viewModel.authError != null) {
                        Surface(
                            color    = Color(0xFFFFEBEE),
                            shape    = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                viewModel.authError!!,
                                color    = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                    WebFormField("Name",    name,     { name = it },     "Enter your full name")
                    WebFormField("Username", username, { username = it }, "Choose a username")
                    WebFormField(
                        label        = "E-mail",
                        value        = email,
                        onValueChange = { email = it },
                        placeholder  = "Enter your email address",
                        keyboardType = KeyboardType.Email,
                        isError      = email.isNotEmpty() && !isValidEmail,
                        errorMsg     = "Invalid email format"
                    )
                    WebPasswordField(
                        label           = "Password",
                        value           = password,
                        onValueChange   = { password = it },
                        placeholder     = "Minimum 8 characters",
                        passwordVisible = passwordVisible,
                        onToggle        = { passwordVisible = !passwordVisible },
                        isError         = password.isNotEmpty() && !isPasswordOk,
                        errorMsg        = "Password must be at least 8 characters"
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick  = { if (fieldsReady) { hasScrolledToEnd = false; showTandCDialog = true } },
                        enabled  = !viewModel.authLoading,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape    = RoundedCornerShape(8.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor         = if (fieldsReady) SteadeNavyBlue else Color(0xFFBBBBBB),
                            contentColor           = Color.White,
                            disabledContainerColor = SteadeNavyBlue.copy(alpha = 0.5f),
                            disabledContentColor   = Color.White.copy(alpha = 0.7f)
                        )
                    ) {
                        if (viewModel.authLoading)
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                        else
                            Text("Sign up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(18.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("Already have an account? ", color = Color(0xFF555555), fontSize = 14.sp)
                        TextButton(
                            onClick        = { navController.navigate(Screen.Login.route) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("sign in", color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

// Shared text field used across login and register forms.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebFormField(
    label        : String,
    value        : String,
    onValueChange: (String) -> Unit,
    placeholder  : String,
    keyboardType : KeyboardType = KeyboardType.Text,
    isError      : Boolean      = false,
    errorMsg     : String       = ""
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(label, color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value           = value,
            onValueChange   = onValueChange,
            placeholder     = { Text(placeholder, color = Color(0xFFAAAAAA)) },
            isError         = isError,
            modifier        = Modifier.fillMaxWidth(),
            shape           = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine      = true,
            colors          = webTextFieldColors()
        )
        if (isError && errorMsg.isNotEmpty())
            Text(errorMsg, color = Color.Red, fontSize = 11.sp)
    }
}

// Shared password field used across login and register forms.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebPasswordField(
    label          : String,
    value          : String,
    onValueChange  : (String) -> Unit,
    placeholder    : String,
    passwordVisible: Boolean,
    onToggle       : () -> Unit,
    isError        : Boolean = false,
    errorMsg       : String  = ""
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Text(label, color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value                = value,
            onValueChange        = onValueChange,
            placeholder          = { Text(placeholder, color = Color(0xFFAAAAAA)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None
                                   else PasswordVisualTransformation(),
            trailingIcon         = {
                IconButton(onClick = onToggle) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            isError              = isError,
            modifier             = Modifier.fillMaxWidth(),
            shape                = RoundedCornerShape(8.dp),
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine           = true,
            colors               = webTextFieldColors()
        )
        if (isError && errorMsg.isNotEmpty())
            Text(errorMsg, color = Color.Red, fontSize = 11.sp)
    }
}

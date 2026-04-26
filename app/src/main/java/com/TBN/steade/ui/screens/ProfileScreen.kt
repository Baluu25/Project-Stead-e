package com.TBN.steade.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.viewmodel.SteadEViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: SteadEViewModel) {
    val context = LocalContext.current
    val prefs   = remember {
        context.getSharedPreferences("ProfilePrefs", android.content.Context.MODE_PRIVATE)
    }
    var profileImageUri by remember {
        mutableStateOf<android.net.Uri?>(
            prefs.getString("profileImageUri", null)?.let { android.net.Uri.parse(it) }
        )
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) { profileImageUri = uri; prefs.edit().putString("profileImageUri", uri.toString()).apply() }
    }
    val user = viewModel.currentUser

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Profile", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))
                // Avatar
                Box(
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)).clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null)
                        Image(rememberAsyncImagePainter(profileImageUri), null,
                            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    else
                        Text("👤", fontSize = 56.sp)
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AddAPhoto, null, tint = Color.White.copy(alpha = 0.85f), modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text(viewModel.getDisplayName(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(viewModel.getDisplayEmail(), color = Color.White.copy(alpha = 0.65f), fontSize = 14.sp)
                Spacer(Modifier.height(28.dp))

                // Info rows matching the real User model fields
                if (user != null) {
                    if (!user.username.isNullOrBlank())         ProfileInfoRow("Username",   user.username)
                    if (!user.gender.isNullOrBlank())           ProfileInfoRow("Gender",     user.gender)
                    if (!user.birthdate.isNullOrBlank())        ProfileInfoRow("Birthdate",  user.birthdate)
                    if (user.weight != null)                    ProfileInfoRow("Weight",     "${user.weight} kg")
                    if (user.height != null)                    ProfileInfoRow("Height",     "${user.height} cm")
                    if (!user.sleepTime.isNullOrBlank())        ProfileInfoRow("Sleep Time", user.sleepTime)
                    if (!user.wakeTime.isNullOrBlank())         ProfileInfoRow("Wake Time",  user.wakeTime)
                    if (!user.userGoal.isNullOrBlank())         ProfileInfoRow("Goal",       user.userGoal)
                    val cats = user.preferredCategoriesText()
                    if (!cats.isNullOrBlank())                  ProfileInfoRow("Categories", cats)
                } else {
                    ProfileInfoRow("Account", viewModel.getDisplayEmail())
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    "Note: Only your profile picture can be changed here. Other data can only be modified via the official website using these same credentials.",
                    color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp,
                    textAlign = TextAlign.Center, lineHeight = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Surface(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        shape = RoundedCornerShape(14.dp), color = Color.White.copy(alpha = 0.12f)) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                Spacer(Modifier.height(2.dp))
                Text(value, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

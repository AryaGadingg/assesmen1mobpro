package org.d3if0094.assesmen1.ui.screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if0094.assesmen1.R

@Composable
fun AboutScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(20.dp)) {
        TopAppBar(navController = navController)
        ProfileInfo()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController) {
    androidx.compose.material3.TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                androidx.compose.material3.Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileInfo() {
    val context = LocalContext.current
    val settingsIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.diri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        ProfileText(jenis = stringResource(R.string.profilnama), isi = ": Arya Gading")
        ProfileText(jenis = stringResource(R.string.profilnim), isi = ": 6706220094")
        ProfileText(jenis = stringResource(R.string.profilkelas), isi = ": D3IF 4605")
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = stringResource(R.string.profiltentang),
            style = TextStyle(fontSize = 30.sp)
        )
        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 20.dp))
        Text(text = stringResource(R.string.profiltujuan))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { context.startActivity(settingsIntent) },
                Modifier.padding(top = 20.dp)
            ) {
                Text(text = stringResource(id = R.string.btnGanti))
            }
        }
    }
}

@Composable
fun ProfileText(jenis: String, isi: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.weight(1.0f)) {
            Text(text = jenis)
        }
        Row(modifier = Modifier.weight(2.0f)) {
            Text(text = isi)
        }
    }
}

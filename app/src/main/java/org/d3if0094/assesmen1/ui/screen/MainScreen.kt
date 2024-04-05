package org.d3if0094.assesmen1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0094.assesmen1.R
import org.d3if0094.assesmen1.navigation.Screen
import org.d3if0094.assesmen1.ui.theme.Assesmen1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.About.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var hargaBarang by rememberSaveable { mutableStateOf("") }
    var hargaBarangError by rememberSaveable { mutableStateOf(false) }

    var diskonPersen by rememberSaveable { mutableStateOf("") }
    var diskonPersenError by rememberSaveable { mutableStateOf(false) }

    var totalBayar by rememberSaveable { mutableStateOf(0f) }
    var jumlahDiskon by rememberSaveable { mutableStateOf(0f) }

    var isResetVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.hitung_intro_diskon),
        )
        OutlinedTextField(
            value = hargaBarang,
            onValueChange = { hargaBarang = it },
            label = { Text(text = stringResource(R.string.harga_barang)) },
            isError = hargaBarangError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        OutlinedTextField(
            value = diskonPersen,
            onValueChange = { diskonPersen = it },
            label = { Text(text = stringResource(R.string.diskon_persen)) },
            isError = diskonPersenError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (isResetVisible) {
                Button(
                    onClick = {
                        hargaBarang = ""
                        diskonPersen = ""
                        hargaBarangError = false
                        diskonPersenError = false
                        totalBayar = 0f
                        jumlahDiskon = 0f
                        isResetVisible = false
                    },
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .weight(1f)
                ) {
                    Text(text = stringResource(R.string.reset))
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Button(
                onClick = {
                    hargaBarangError = (hargaBarang.isBlank() || hargaBarang == "0")
                    diskonPersenError = (diskonPersen.isBlank() || diskonPersen == "0")
                    if (hargaBarangError || diskonPersenError) return@Button

                    totalBayar = hitungTotalBayar(hargaBarang.toFloat(), diskonPersen.toFloat())
                    jumlahDiskon = hitungJumlahDiskon(hargaBarang.toFloat(), diskonPersen.toFloat())

                    isResetVisible = true
                },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .weight(1f)
            ) {
                Text(text = stringResource(R.string.hitung))
            }

        }


        if (totalBayar != 0f && jumlahDiskon != 0f) {
            Divider()
            Text(text = stringResource(R.string.total_bayar, totalBayar))
            Text(text = stringResource(R.string.jumlah_diskon, jumlahDiskon))
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(
                            R.string.bagikan_template_diskon,
                            hargaBarang, diskonPersen, totalBayar, jumlahDiskon
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

private fun hitungTotalBayar(hargaBarang: Float, diskonPersen: Float): Float {
    val diskon = hargaBarang * (diskonPersen / 100)
    return hargaBarang - diskon
}

private fun hitungJumlahDiskon(hargaBarang: Float, diskonPersen: Float): Float {
    return hargaBarang * (diskonPersen / 100)
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    val chooserIntent = Intent.createChooser(shareIntent, "Bagikan via")
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooserIntent)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Assesmen1Theme {
        MainScreen(rememberNavController())
    }
}
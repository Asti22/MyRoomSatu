package com.example.myroomsatu.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myroomsatu.R
import com.example.myroomsatu.view.viewmodel.DetailSiswa
import com.example.myroomsatu.view.viewmodel.UIStateSiswa
import com.example.myroomsatu.view.viewmodel.EntryViewModel
import com.example.myroomsatu.view.navigasi.DestinasiEntry
import com.example.myroomsatu.view.uikomponen.SiswaTopAppBar
import com.example.myroomsatu.view.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = DestinasiEntry.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            onSiswaValueChange = viewModel::updateUIState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveSiswa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_medium)
        ),
        modifier = modifier.padding(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.Submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswa(
    detailSiswa: DetailSiswa,
    modifier: Modifier = Modifier,
    onValueChange: (DetailSiswa) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = { onValueChange(detailSiswa.copy(nama = it)) },
            label = { Text(text = stringResource(R.string.NamaSiswa)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = { onValueChange(detailSiswa.copy(alamat = it)) },
            label = { Text(text = stringResource(R.string.AlamatSiswa)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = { onValueChange(detailSiswa.copy(telpon = it)) },
            label = { Text(text = stringResource(R.string.TelponSiswa)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.requiredFields),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.padding_small)
            ),
            thickness = 1.dp, // langsung dp
            color = Color.Blue
        )
    }
}

package com.example.myroomsatu.view.uicontroller

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(markerClass = ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = DestinasiHome.titleRes),
                canNavigateBack = false, // Ini adalah layar utama, tidak ada tombol kembali.
                scrollBehavior = scrollBehavior
            )
},
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry, // Aksi: navigasi ke layar entry siswa
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(all = dimensionResource(id = 20.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.TambahSiswa) // Deskripsi aksesibilitas
                )
            }
        },
    ) { innerPadding ->
        val uiStateSiswa by viewModel.homeUiState.collectAsState()

        // Konten utama layar
        BodyHome(
            itemSiswa = uiStateSiswa.listSiswa, // Mengambil daftar siswa dari UI State
            modifier = Modifier
                .padding(paddingValues = innerPadding) // Menerapkan padding dari Scaffold
                .fillMaxSize()
        )
    }
}
@Composable
fun BodyHome(
    itemSiswa: List<Siswa>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        if (itemSiswa.isEmpty()) {
            Text(
                text = stringResource(R.string.TidakAdaDataSiswa), // Pesan 'Data Kosong'
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Tap + untuk menambah data", // Instruksi tambahan
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            ListSiswa(
                itemSiswa = itemSiswa,
                modifier = Modifier.padding(horizontal = dimensionResource(id = 8.dp))
            )
        }
    }
}


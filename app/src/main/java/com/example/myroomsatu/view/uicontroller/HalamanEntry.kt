package com.example.myroomsatu.view.uicontroller

@OptIn(markerClass = ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope() // CoroutineScope untuk menjalankan fungsi suspend (saveSiswa)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = DestinasiEntry.titleRes),
                canNavigateBack = true, // Tombol kembali diaktifkan
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        // Konten utama layar entry
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            // Fungsi yang dipanggil saat nilai input berubah
            onSiswaValueChange = viewModel::updateUIState,
            // Fungsi yang dipanggil saat tombol Submit ditekan
            onSaveClick = {
                // Meluncurkan coroutine untuk memanggil fungsi suspend saveSiswa
                coroutineScope.launch {
                    viewModel.saveSiswa()
                    navigateBack() // Kembali setelah data berhasil disimpan
                }
            },
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .verticalScroll(state = rememberScrollState()) // Membuat konten bisa digulir
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
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = 20.dp)),
        modifier = modifier.padding(all = dimensionResource(id = 16.dp))
    ) {
        // Komponen Form Input Siswa
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol Submit/Simpan
        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid, // Hanya aktif jika input valid
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.Submit)) // Pastikan R.string.Submit ada
        }
    }
}
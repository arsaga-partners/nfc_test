package com.example.nfc_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nfc_test.ui.theme.NFC_TESTTheme
import kotlinx.coroutines.launch
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ReaderCallback
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import java.lang.StringBuilder
import java.util.*


class MainActivity : ComponentActivity() {

    var nfcAdapter: NfcAdapter? = null
    var activity = this@MainActivity

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        setContent {
            NFC_TESTTheme {
                NfcTest(nfcAdapter, activity)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NfcTest(nfcAdapter: NfcAdapter?, activity: MainActivity?) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var id by rememberSaveable { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "スキャンの準備が出来ました")

                Box(
                    modifier = Modifier.size(56.dp)
                )

                Button(
                    onClick = {
                        id = "キャンセル"
                        nfcAdapter?.disableReaderMode(activity)
                        scope.launch { state.hide() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "キャンセル",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "NFC TEST")
                    }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(32.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ResultId(id = id, onIdChange = { id = it })

                Button(
                    onClick = {
                        id = "スキャン"
                        nfcAdapter?.enableReaderMode(
                            activity,
                            MyReaderCallback(),
                            NfcAdapter.FLAG_READER_NFC_F,
                            null
                        )
                        scope.launch { state.show() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "NFCスキャン",
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ResultId(id: String, onIdChange: (String) -> Unit) {
    OutlinedTextField(
        value = id,
        onValueChange = onIdChange,
        label = { Text("Read ID ..") }
    )
}

private class MyReaderCallback : ReaderCallback {
    override fun onTagDiscovered(tag: Tag) {
        Log.d("Hoge", "Tag discoverd.")

        //get idm
        val idm = tag.id
        val idmString = bytesToHexString(idm)
        Log.d("Hoge", idmString)
    }
}

fun bytesToHexString(bytes: ByteArray): String {
    val sb = StringBuilder()
    val formatter = Formatter(sb)
    for (b in bytes) {
        formatter.format("%02x", b)
    }
    return sb.toString().uppercase(Locale.getDefault())
}


@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NfcTest(null, null)
}
package com.example.nfc_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfc_test.ui.theme.NFC_TESTTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var content = "test"

        setContent {
            NFC_TESTTheme {
                NfcTest(content)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NfcTest(content: String) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "スキャンの準備が出来ました")
                
                Box(
                    modifier = Modifier.size(56.dp)
                )

                Button(
                    onClick = { /*TODO*/ },
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
                Text(
                    text = content,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 4.dp, color = Color.Black)
                        .padding(32.dp)
                )
                Button(
                    onClick = { scope.launch { state.show() } },
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

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NfcTest(content = "")
}
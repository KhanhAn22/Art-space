package com.example.artspace

import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Artwork(val imageResId: Int, val titleResId: Int, val artistResId: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen() {
    val artworks = listOf(
        Artwork(R.drawable.adam, R.string.art_creation_of_adam, R.string.artist_michelangelo),
        Artwork(R.drawable.mona, R.string.art_mona_lisa, R.string.artist_da_vinci),
        Artwork(R.drawable.picture, R.string.art_title, R.string.artist_name)
    )

    var currentIndex by remember { mutableStateOf(0) }

    fun showNextArtwork() {
        currentIndex = if (currentIndex < artworks.size - 1) currentIndex + 1 else 0
    }

    fun showPreviousArtwork() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else artworks.size - 1
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Art Space", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 8.dp,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Image(
                    painter = painterResource(id = artworks[currentIndex].imageResId),
                    contentDescription = stringResource(id = artworks[currentIndex].titleResId),
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(8.dp))
//                        .border(1.dp, color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,) {
                        Text(
                            text = stringResource(id = artworks[currentIndex].titleResId),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(id = artworks[currentIndex].artistResId),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Button(onClick = { showPreviousArtwork() }) {
                        Text(text = stringResource(R.string.previous))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { showNextArtwork() }) {
                        Text(text = stringResource(R.string.next))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtPreview() {
    ArtSpaceTheme {
        ArtScreen()
    }
}

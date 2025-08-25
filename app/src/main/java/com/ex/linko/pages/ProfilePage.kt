package com.ex.linko.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfilePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Üst bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("username", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Row {
                IconButton(onClick = { /* Menu */ }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
                IconButton(onClick = { /* Settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profil resmi ve istatistikler
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Profil resmi
            Image(
                painter = rememberAsyncImagePainter("https://via.placeholder.com/150"),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            // İstatistikler
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("120", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Gönderi", fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("5.4k", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Takipçi", fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("300", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Takip", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // İsim ve biyografi
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Ad Soyad", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Biyografi burada yer alır. ✨📷 #Compose")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Takip et / Mesaj / Düzenle Butonları
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { /* Takip et */ }, modifier = Modifier.weight(1f)) {
                Text("Takip Et")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /* Mesaj */ }, modifier = Modifier.weight(1f)) {
                Text("Mesaj")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = { /* Düzenle */ }, modifier = Modifier.weight(1f)) {
                Text("Düzenle")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hikayeler (Stories) LazyRow
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(10) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Story $index", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gönderi alanı placeholder
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )
        }
    }
}

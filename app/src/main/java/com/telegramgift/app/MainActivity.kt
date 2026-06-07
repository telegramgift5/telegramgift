package com.telegramgift.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0F0F0F)) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedChat by remember { mutableStateOf<String?>(null) }

    if (selectedChat != null) {
        ChatScreen(chatName = selectedChat!!, onBack = { selectedChat = null })
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💎 TelegramGift", color = Color(0xFFE5A93D), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF161616))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1C1C1E)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Chat, null) },
                    label = { Text("Чаты") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CardGiftcard, null) },
                    label = { Text("Подарки") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Настройки") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> ChatList(modifier = Modifier.padding(padding), onChatClick = { selectedChat = it })
            1 -> Gifts(modifier = Modifier.padding(padding))
            2 -> Settings(modifier = Modifier.padding(padding))
        }
    }
}

@Composable
fun ChatList(modifier: Modifier, onChatClick: (String) -> Unit) {
    val chats = listOf("Анна", "Работа", "Семья", "Друзья", "Premium Support")
    LazyColumn(modifier = modifier.background(Color(0xFF0F0F0F))) {
        items(chats) { name ->
            Surface(modifier = Modifier.fillMaxWidth().clickable { onChatClick(name) }, color = Color.Transparent) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(52.dp).clip(CircleShape).background(Color(0xFF2AABEE)), contentAlignment = Alignment.Center) {
                        Text(name.first().uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(name, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chatName: String, onBack: () -> Unit) {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chatName, color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF161616))
            )
        },
        bottomBar = {
            Surface(color = Color(0xFF1C1C1E)) {
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Сообщение", color = Color.White.copy(alpha = 0.3f)) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent),
                        shape = RoundedCornerShape(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { if (message.isNotBlank()) { messages = messages + message; message = "" } }) {
                        Icon(Icons.Default.Send, null, tint = Color(0xFF2AABEE))
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFF0F0F0F)).padding(16.dp)) {
            items(messages) { msg ->
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = Alignment.CenterEnd) {
                    Surface(color = Color(0xFF2AABEE), shape = RoundedCornerShape(16.dp)) {
                        Text(msg, color = Color.White, modifier = Modifier.padding(12.dp), fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun Gifts(modifier: Modifier) {
    val gifts = listOf("🌹 Роза" to 15, "💎 Кристалл" to 50, "👑 Корона" to 100, "🚀 Ракета" to 75, "🦄 Единорог" to 200, "⭐ Звезда" to 10)
    Column(modifier = modifier.fillMaxSize().background(Color(0xFF0F0F0F)).padding(16.dp)) {
        Text("🎁 Магазин подарков", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("⭐ 250 звёзд", color = Color(0xFFE5A93D), fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(gifts) { (name, price) ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)), shape = RoundedCornerShape(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(name.first().toString(), fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(name.drop(2), color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
                        Text("$price ⭐", color = Color(0xFFE5A93D), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun Settings(modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize().background(Color(0xFF0F0F0F)).padding(16.dp)) {
        Text("⚙️ Настройки", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)), shape = RoundedCornerShape(12.dp)) {
            Column {
                listOf("👤 Профиль", "🔔 Уведомления", "🔒 Конфиденциальность", "🎨 Оформление", "💾 Данные", "ℹ️ О приложении").forEach { text ->
                    Surface(modifier = Modifier.fillMaxWidth().clickable { }, color = Color.Transparent) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text, color = Color.White, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ChevronRight, null, tint = Color.White.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Версия 1.0", color = Color.White.copy(alpha = 0.3f), fontSize = 12.sp)
    }
}

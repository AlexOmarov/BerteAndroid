package com.example.berteandroid

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.berteandroid.ui.theme.BerteAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FirstScreen() }
    }
}

data class ElementData(val header: String, val description: String)

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun FirstScreen() {
    BerteAndroidTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserList(
                listOf(
                    ElementData(
                        "John McFaul",
                        "Foolish description for record\nFoolish description for record\nFoolish description for record\nFoolish description for record"
                    ),
                    ElementData("John McFaul", "Foolish description for record"),
                    ElementData("John McFaul", "Foolish description for record"),
                    ElementData("John McFaul", "Foolish description for record"),
                )
            )
        }
    }
}

@Composable
fun UserList(data: List<ElementData>) {
    LazyColumn {
        items(data) { data ->
            UserListRow(data)
        }
    }
}

@Composable
fun UserListRow(data: ElementData) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        UserListRowImage()

        Spacer(modifier = Modifier.width(8.dp))

        UserListRowContent(data)

    }
}

@Composable
fun UserListRowImage() {
    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "Contact profile picture",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
    )
}

@Composable
fun UserListRowContent(data: ElementData) {
    // We keep track if the message is expanded or not in this
    // variable
    var isExpanded by remember { mutableStateOf(false) }

    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
        Text(
            text = data.header,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
            color = surfaceColor,
            modifier = Modifier.animateContentSize().padding(1.dp)
        ) {
            Text(
                text = data.description,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
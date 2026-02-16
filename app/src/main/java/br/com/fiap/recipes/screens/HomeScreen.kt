package br.com.fiap.recipes.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.recipes.ui.theme.RecipesTheme
import br.com.fiap.recipes.R

@Composable
fun HomeScreen(navController: NavController, email: String?) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { MyTopAppBar(email!!) },
            bottomBar = { MyBottomAppBar() },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add button"
                    )
                }
            },
        ){ paddingValues ->
            ContentScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    RecipesTheme {
        HomeScreen(rememberNavController(), "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(email: String) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, João!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
                Card(
                    modifier = Modifier
                        .size(48.dp),
                    shape = CircleShape,
                    colors = CardDefaults
                        .cardColors(
                            containerColor = Color.Transparent
                        ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "User image"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun MyTopAppBarPreview() {
    RecipesTheme {
        MyTopAppBar("")
    }
}

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun MyBottomAppBar(modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavigationItem("Home", icon = Icons.Default.Home),
        BottomNavigationItem("Favorites", icon = Icons.Default.Favorite),
        BottomNavigationItem("Profile", icon = Icons.Default.Person),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        items.forEach{ item ->
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            )
        }
    }

}

@Preview
@Composable
private fun MyBottomAppBarPreview() {
    RecipesTheme {
        MyBottomAppBar()
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0XFFF5F5F5)
                ),
            label = {
                Text(
                    text = stringResource(R.string.search_by_recipes),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .height(116.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.cooking_card),
                contentDescription = "Cooking",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(116.dp))
        Text(
            text = stringResource(R.string.newly_added_recipes),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ContentScreenPreview() {
    RecipesTheme {
        ContentScreen()
    }
}




















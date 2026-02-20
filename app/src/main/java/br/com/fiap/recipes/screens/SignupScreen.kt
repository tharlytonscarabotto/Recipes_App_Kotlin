package br.com.fiap.recipes.screens

import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.recipes.R
import br.com.fiap.recipes.ui.theme.RecipesTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import br.com.fiap.recipes.model.User
import br.com.fiap.recipes.navigation.Destination
import br.com.fiap.recipes.repository.SharedPreferenciesUserRepository

@Composable
fun SignupScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        TopEndCard(modifier = Modifier.align(Alignment.TopEnd))
        BottomStarCard(modifier = Modifier.align(Alignment.BottomStart))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComponent()
            Spacer(modifier = Modifier.height(48.dp))
            UserImage()
            SignupUserForm(navController)
        }

    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun SignupScreenPreview() {
    RecipesTheme {
        SignupScreen(rememberNavController())
    }

}

@Composable
fun TitleComponent(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.sign_up),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.create_account),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TitleComponentPreview() {
    RecipesTheme {
        TitleComponent()
    }
}

@Composable
fun UserImage(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(120.dp)
    ){
        Image(
            painter = painterResource(R.drawable.user),
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier
                .size(110.dp)
                .align(alignment = Alignment.Center)
        )
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = stringResource(R.string.camera_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun UserImagePreview() {
    RecipesTheme {
        UserImage()
    }
}

@Composable
fun SignupUserForm(navController: NavController) {

    //Variaveis de estado para controlar os valores exibidos nos OutlinedTextFields
    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    //Variaveis de estado para verificar se os dados estao corretos
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    //Variavel de estado para controlar a exibição da mensagem de erro
    var showDialogError by remember { mutableStateOf(false) }
    var showDialogSuccess by remember { mutableStateOf(false) }

    //Funcao para verificar se os dados estao corretos
    fun validate(): Boolean{
        isNameError = name.length < 3
        isEmailError = email.length < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.length < 3
        return !isNameError && !isEmailError && !isPasswordError
    }

    //Criação de uma instancia do SharedPreferencesUserRepository
    val userRepository = SharedPreferenciesUserRepository(context = LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_name),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.person_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
           isError = isNameError,
           trailingIcon = {
               if (isNameError){
                   Icon(
                       imageVector = Icons.Default.Error,
                       contentDescription = "Error",
                       tint = MaterialTheme.colorScheme.error
                   )
               }
           },
           supportingText = {
               if (isNameError){
                   Text(
                       text = stringResource(R.string.name_must_have_at_least_3_characters),
                       color = MaterialTheme.colorScheme.error,
                       modifier = Modifier.fillMaxWidth(),
                       textAlign = TextAlign.End
                   )
               }
           }
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_e_mail),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = stringResource(R.string.email_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            isError = isEmailError,
            trailingIcon = {
                if (isEmailError){
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isEmailError){
                    Text(
                        text = "Wrong format for an email",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        )
        //Caixa de texto da senha
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_password),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.lock_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            isError = isPasswordError,
            trailingIcon = {
                if (isPasswordError){
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isPasswordError){
                    Text(
                        text = "Password must have at least three characters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if(validate()){
                    userRepository.saveUser(
                        User(
                            name = name,
                            email = email,
                            password = password
                        )
                    )
                    showDialogSuccess = true
                } else {
                    showDialogError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
    //Caixa de dialogo de sucesso
    if(showDialogSuccess){
        AlertDialog(
            onDismissRequest = {showDialogError = false},
            title = {
                Text(text = "Success")
            },
            text = {
                Text(text = "Account created successfully")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogSuccess = false
                        navController.navigate(Destination.LoginScreen.route)
                    }
                ) {
                    Text(text = "OK")
                }
            }

        )
    }


    //Caixa de dialogo de erro
    if(showDialogError){
        AlertDialog(
            onDismissRequest = {showDialogError = false},
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = "Please fill in all fields correctly")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogError = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun SignupUserFormPreview() {
    RecipesTheme {
        SignupUserForm(rememberNavController())
    }
}
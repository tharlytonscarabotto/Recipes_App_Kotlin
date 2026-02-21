package br.com.fiap.recipes.screens

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Patterns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.User
import br.com.fiap.recipes.navigation.Destination
import br.com.fiap.recipes.repository.RoomUserRepository
import br.com.fiap.recipes.ui.theme.RecipesTheme
import br.com.fiap.recipes.utils.convertBitmapToByteArray

@Composable
fun ProfileScreen(navController: NavController, email: String?) {

    val context = LocalContext.current

    //Variavel que armazena a imagem padrao do usuario
    val placeholderImage = BitmapFactory
        .decodeResource(
            Resources.getSystem(),
            android.R.drawable.ic_menu_gallery
        )

    //Armazenar a imagem de  profile do usuario em uma variavel BitMap
    var profileImage by remember {
        mutableStateOf<Bitmap>(placeholderImage)
    }

    //Criar um lancador de atividade para buscar imagem da galeria
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){ uri ->
        if(Build.VERSION.SDK_INT < 28){
            profileImage = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            if(uri != null){
                val source = ImageDecoder
                    .createSource(context.contentResolver, uri)
                profileImage = ImageDecoder.decodeBitmap(source)
            } else{
                profileImage = placeholderImage
            }
        }
    }

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
            ProfileTitleComponent()
            Spacer(modifier = Modifier.height(48.dp))
            ProfileUserImage(profileImage, launcher)
            ProfileUserForm(navController, profileImage, email)
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    RecipesTheme {
        ProfileScreen(rememberNavController(), "")
    }
}

@Composable
fun ProfileTitleComponent(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.profile),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.user_profile_details),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun ProfileUserImage(profileImage: Bitmap, launcher: ManagedActivityResultLauncher<String, Uri?>) {
    Box(
        modifier = Modifier
            .size(120.dp)
    ){
        Image(
            bitmap = profileImage.asImageBitmap(),
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(110.dp)
                .align(alignment = Alignment.Center),
            contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = stringResource(R.string.camera_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .clickable(
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
        )
    }
}

@Composable
fun ProfileUserForm(
    navController: NavController,
    profileImage: Bitmap,
    userEmail: String?
) {

    //Criação de uma instancia do SharedPreferencesUserRepository
    //val userRepository = SharedPreferenciesUserRepository(context = LocalContext.current)
    val userRepository = RoomUserRepository(context = LocalContext.current)
    var user = userRepository.getUserByEmail(userEmail!!)

    //Variaveis de estado para controlar os valores exibidos nos OutlinedTextFields
    var name by remember {
        mutableStateOf(user!!.name)
    }

    var email by remember {
        mutableStateOf(user!!.email)
    }

    var password by remember {
        mutableStateOf(user!!.password)
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

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

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
                    userRepository.update(
                        User(
                            id = user!!.id,
                            name = name,
                            email = email,
                            password = password,
                            userImage = convertBitmapToByteArray(profileImage)
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
                text = stringResource(R.string.update_profile),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Delete profile",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiary
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
                Text(text = "Account updated successfully")
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

    //Caixa de dialogo de exclusao
    if(showDeleteDialog){
        AlertDialog(
            onDismissRequest = {showDeleteDialog = false},
            title = {
                Text(
                    text = stringResource(R.string.user_removal)
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.are_you_sure_you_want_to_delete_this_account)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        if (user != null){
                            userRepository.delete(user)
                            navController.navigate(Destination.LoginScreen.route)
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cancel)
                    )
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
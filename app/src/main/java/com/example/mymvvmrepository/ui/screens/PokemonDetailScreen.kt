package com.example.mymvvmrepository.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mymvvmrepository.ui.components.ErrorView
import com.example.mymvvmrepository.ui.components.LoadingView
import com.example.mymvvmrepository.ui.viewmodel.PokemonDetailState
import com.example.mymvvmrepository.ui.viewmodel.PokemonDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
     viewModel: PokemonDetailViewModel = hiltViewModel(),
     navController: NavHostController
){
     val state = viewModel.state.collectAsState().value


     Scaffold(
          topBar = {
               CenterAlignedTopAppBar(
                    title = {
                         Text(
                              text = "Pokédex",
                              style = MaterialTheme.typography.headlineSmall
                         )
                    },
                    navigationIcon = {
                         if (navController.previousBackStackEntry != null) {
                              IconButton(onClick = { navController.popBackStack() }) {
                                   Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Volver"
                                   )
                              }
                         }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                         containerColor = MaterialTheme.colorScheme.primaryContainer,
                         titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
               )
          }
     ) { innerPadding ->

          Box(modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding)) {
               when (state) {
                    is PokemonDetailState.Loading -> {
                         LoadingView(modifier = Modifier.align(Alignment.Center))
                    }

                    is PokemonDetailState.Success -> {

                         Card(
                              modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(8.dp),
                              elevation = CardDefaults.cardElevation(4.dp)
                         ) {
                              Column (
                                   modifier = Modifier.fillMaxWidth(),
                                   verticalArrangement = Arrangement.Center,
                                   horizontalAlignment = Alignment.CenterHorizontally) {
                                   AsyncImage(model =  state.pokemon.imageUrl,contentDescription = state.pokemon.name,
                                        modifier = Modifier.size(64.dp),
                                        contentScale = ContentScale.Fit)
                                   Spacer(modifier = Modifier.width(16.dp))
                                   Text(
                                        text = state.pokemon.name.replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.headlineLarge
                                   )
                                   Spacer(modifier = Modifier.width(16.dp))
                                   Text(
                                        text ="Altura: "+ state.pokemon.height,
                                        style = MaterialTheme.typography.titleMedium
                                   )
                                   Spacer(modifier = Modifier.width(16.dp))
                                   Text(
                                        text ="Peso: "+ state.pokemon.weight,
                                        style = MaterialTheme.typography.titleMedium
                                   )
                                   Spacer(modifier = Modifier.width(16.dp))
                              }
                         }
                    }

                    is PokemonDetailState.Error -> {
                         ErrorView(
                              message = state.message,
                              onRetry = state.retryAction,
                              modifier = Modifier.align(Alignment.Center)
                         )
                    }
               }
          }
     }
}
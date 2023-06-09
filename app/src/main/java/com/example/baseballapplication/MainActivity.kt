package com.example.baseballapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.baseballapplication.ui.theme.BaseballApplicationTheme
import com.example.baseballapplication.ui.theme.MenuColor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class MainActivity : ComponentActivity() {

    val sheetId = "1pYb2Di5YoF0IbpV0kq1D25J_Vv-JGPWu7_yE0kAxFS0"
    val apiKEY = "AIzaSyCfkvR4qvJbbL7ObotNmf68XA1Ghc4abnM"

    var players = ArrayList<PlayersModel>()
    var teams = ArrayList<TeamModel>()
    var jsonArray = JSONArray()
    var stadiums = ArrayList<StadiumModel>()
   companion object{
       const val favMode = "favourite"
       const val teamMode = "team"
       const val allMode = "all"
   }


    fun nameFormatter(number: String, name: String) : String {
        return "#$number $name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = findViewById<View>(android.R.id.content)
        root.setBackgroundColor(Color.Black.toArgb())
        val url = "https://sheets.googleapis.com/v4/spreadsheets/"+sheetId+"/values/BAT?key="+apiKEY
        val queue = Volley.newRequestQueue(this)
        val playerDB by lazy { PlayerDatabase.getDatabase(this).playerDao() }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
            try {
                jsonArray = response.getJSONArray("values")
            } catch (e: Exception) {
                Log.i("JSONExc", e.toString())
            }

            for (i in 1 until jsonArray.length()) {
                try {
                    val json = jsonArray.getJSONArray(i)
                    val strName = json.getString(1)
                    val strNumber = json.getString(0)

                    var statString = ""
                    for (l in 2..37) {
                        statString += json.getString(l).replace(",",".")+" "
                        Log.i("stat", json.getString(37))
                    }
                    val game = json.getInt(2)
                    val pa = json.getInt(3)
                    val rbi = json.getInt(12)
                    val ops = json.getString(36).replace(",",".").toDouble()
                    val team = json.getString(38)
                    val img = json.getString(39)
                    CoroutineScope(Dispatchers.IO).launch {
                        if (playerDB.checkIfFavorite(nameFormatter(strNumber,strName))) {
                            val player = PlayersModel(nameFormatter(strNumber,strName),
                                game,pa,rbi,ops,team,img, statString, true)
                            playerDB.insertPlayer(player)
                        } else {
                            val player = PlayersModel(nameFormatter(strNumber,strName),
                                game,pa,rbi,ops,team,img, statString, false)
                            playerDB.insertPlayer(player)
                        }

                    }


                } catch(e: Exception) {
                    Log.i("DBe", e.toString())
                }


            }
        },
            { error ->
                Log.i("DBError",error.toString())
                // TODO: Handle error
            }
        )
        CoroutineScope(Dispatchers.IO).launch {
            players = playerDB.getAllPlayers() as ArrayList<PlayersModel>
            players.sortBy { -it.stat4 }
        }

        queue.add(jsonObjectRequest)

        val teamUrl = "https://sheets.googleapis.com/v4/spreadsheets/"+sheetId+"/values/TEAM?key="+apiKEY
        val teamDB by lazy { PlayerDatabase.getDatabase(this).teamDao() }


        val teamJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, teamUrl, null, { response ->
                try {
                    jsonArray = response.getJSONArray("values")
                } catch (e: Exception) {
                    Log.i("JSONExc", e.toString())
                }

                for (i in 1 until jsonArray.length()) {
                    try {
                        val json = jsonArray.getJSONArray(i)
                        val teamName = json.getString(0)
                        val wins = json.getInt(1)
                        val losses = json.getInt(2)
                        val logoImg = json.getString(3)
                        val shortName = json.getString(4)



                        CoroutineScope(Dispatchers.IO).launch {
                            if (teamDB.checkIfFavorite(shortName)) {
                                val team = TeamModel(teamName,wins,losses,logoImg,shortName,true)
                                teamDB.insertTeam(team)
                            } else {
                                val team = TeamModel(teamName,wins,losses,logoImg,shortName,false)
                                teamDB.insertTeam(team)
                            }

                        }


                    } catch(e: Exception) {
                        Log.i("DBeTeam", e.toString())
                    }


                }
            },
            { error ->
                Log.i("DBError",error.toString())
                // TODO: Handle error
            }
        )
        CoroutineScope(Dispatchers.IO).launch {
            teams = teamDB.getAllTeams() as ArrayList<TeamModel>

            teams.sortBy { it.shortName }

        }

        queue.add(teamJsonObjectRequest)
        val stadiumUrl = "https://sheets.googleapis.com/v4/spreadsheets/"+sheetId+"/values/STADIUM?key="+apiKEY
        val stadiumDB by lazy { PlayerDatabase.getDatabase(this).stadiumDao() }


        val stadiumJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, stadiumUrl, null, { response ->
                try {
                    jsonArray = response.getJSONArray("values")
                } catch (e: Exception) {
                    Log.i("JSONExc", e.toString())
                }

                for (i in 1 until jsonArray.length()) {
                    try {
                        val json = jsonArray.getJSONArray(i)
                        val fieldName = json.getString(0)
                        val lat = json.getString(1)
                        val ln = json.getString(2)
                        val logoImg = json.getString(3)
                        val city = json.getString(4)
                        val address = json.getString(5)


                        CoroutineScope(Dispatchers.IO).launch {
                            val stadium = StadiumModel(fieldName,lat,ln,logoImg,city,address)
                            stadiumDB.insertStadium(stadium)

                        }


                    } catch(e: Exception) {
                        Log.i("DBeStadium", e.toString())
                    }


                }
            },
            { error ->
                Log.i("DBError",error.toString())
                // TODO: Handle error
            }
        )
        CoroutineScope(Dispatchers.IO).launch {
            stadiums = stadiumDB.getAllStadiums() as ArrayList<StadiumModel>

            stadiums.sortBy { it.name }

        }
        queue.add( stadiumJsonObjectRequest)

        setContent {
            BaseballApplicationTheme {
                MainMenu()
            }
        }

    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> val data = result.data
    }

    private fun showTeams() {
        val myIntent = Intent(this, TeamsActivity::class.java)
        myIntent.putExtra("mode", allMode)
        myIntent.putParcelableArrayListExtra("teams",teams)
        resultLauncher.launch(myIntent)

    }

    private fun showFavoritePLayers() {
        val myIntent = Intent(this, PlayersActivity::class.java)
        myIntent.putExtra("mode", favMode)
        var favoritePlayers = ArrayList<PlayersModel>()
        val playerDB by lazy { PlayerDatabase.getDatabase(this).playerDao() }
        CoroutineScope(Dispatchers.IO).launch {
            favoritePlayers = playerDB.getFavoritePlayers() as ArrayList<PlayersModel>
            favoritePlayers.sortBy { it.stat4 }
            myIntent.putParcelableArrayListExtra("players",favoritePlayers)
            Log.i("favplayers",favoritePlayers.size.toString())
            //myIntent.putParcelableArrayListExtra("players",players)
            resultLauncher.launch(myIntent)
        }
    }
    private fun showStadiums() {
        val myIntent = Intent(this, StadiumsActivity::class.java)
        myIntent.putParcelableArrayListExtra("stadiums",stadiums)
        Log.i("stadiums",stadiums.size.toString())
        resultLauncher.launch(myIntent)

    }

    private fun showFavouriteTeams() {
        val myIntent = Intent(this, TeamsActivity::class.java)
        myIntent.putExtra("mode", favMode)
        var favoriteTeams = ArrayList<TeamModel>()
        val teamsDB by lazy { PlayerDatabase.getDatabase(this).teamDao() }
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTeams = teamsDB.getFavoriteTeams() as ArrayList<TeamModel>
            favoriteTeams.sortBy { it.shortName }
            myIntent.putParcelableArrayListExtra("teams",favoriteTeams)

            resultLauncher.launch(myIntent)
        }

    }


    fun showPlayers() {
        val intent = Intent(this, PlayersActivity::class.java)
        intent.putExtra("mode", allMode)
        intent.putParcelableArrayListExtra("players",players)
        startActivity(intent)


    }


    @Composable
    fun MainMenu() {
        val weight = 0.35f
        val padding = 5
        Column (Modifier.background(color = MenuColor)) {

            Box( Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
            {
                Text(text = "EKSTRALIGA 2023 STATYSTYKI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    fontFamily = FontFamily.Monospace,
                )

            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp)

            ) {

                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.hsv(0f,0f,0.29f))
                        .clickable(onClick = {
                            showPlayers()
                        }),
                        contentAlignment = Alignment.Center,

                        ) {

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {




                            Box(contentAlignment = Alignment.Center)
                            {
                                Text(
                                    text = "Zawodnicy",
                                    modifier = Modifier.padding(padding.dp),
                                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                                )

                            }
                            Image(
                                painter = painterResource(R.drawable.player),
                                contentDescription = "My Image",
                                modifier = Modifier
                                    .weight(weight)
                                    .fillMaxHeight()
                                    .height(200.dp),
                                contentScale = ContentScale.FillHeight,
                            )

                        }



                    }

                }


                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.hsv(0f,0f,0.29f))
                        .clickable(onClick = {
                            showTeams()
                        }),
                        contentAlignment = Alignment.Center,

                        ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {




                            Box(contentAlignment = Alignment.Center)
                            {
                                Text(
                                    text = "Drużyny",
                                    modifier = Modifier.padding(padding.dp),
                                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                                )

                            }
                            Image(
                                painter = painterResource(R.drawable.silesiarybnik),
                                contentDescription = "My Image",
                                modifier = Modifier
                                    .weight(weight)
                                    .fillMaxHeight()
                                    .height(200.dp),
                                contentScale = ContentScale.FillHeight,
                            )

                        }


                    }

                }


                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.hsv(0f,0f,0.29f))
                        .clickable(onClick = {
                            showStadiums()
                        }),
                        contentAlignment = Alignment.Center,

                        ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(contentAlignment = Alignment.Center)
                            {
                                Text(
                                    text = "Stadiony",
                                    modifier = Modifier.padding(padding.dp),
                                    style = TextStyle(color = Color.White, fontSize = 18.sp)
                                )

                            }
                            Image(
                                painter = painterResource(R.drawable.boisko),
                                contentDescription = "My Image",
                                modifier = Modifier
                                    .weight(weight)
                                    .fillMaxHeight()
                                    .height(200.dp),
                                contentScale = ContentScale.FillHeight,
                            )




                        }


                    }

                }


                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.hsv(0f,0f,0.29f))
                        .clickable(onClick = {
                            showFavouriteTeams()
                        }),
                        contentAlignment = Alignment.Center,

                        ) {

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {



                            Box(contentAlignment = Alignment.TopCenter)
                            {
                                Text(
                                    text = "Ulubione Drużyny",
                                    modifier = Modifier.padding(padding.dp),
                                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                                )

                            }
                            Box(contentAlignment = Alignment.Center)
                            {
                                Image(
                                    painter = painterResource(R.drawable.baseline_star_24_yellow),
                                    contentDescription = "My Image",
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .height(200.dp),
                                    contentScale = ContentScale.FillHeight,
                                )

                            }
                        }



                    }

                }

                item {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.hsv(0f,0f,0.29f))
                        .clickable(onClick = {
                            showFavoritePLayers()
                        }),
                        contentAlignment = Alignment.Center,

                        ) {

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {




                            Box(contentAlignment = Alignment.Center)
                            {
                                Text(
                                    text = "Ulubieni zawodnicy",
                                    modifier = Modifier.padding(padding.dp).align(Alignment.Center),
                                    style = TextStyle(color = Color.White, fontSize = 19.sp)
                                )

                            }
                            Image(
                                painter = painterResource(R.drawable.przyklad4),
                                contentDescription = "My Image",
                                modifier = Modifier
                                    .weight(weight)
                                    .fillMaxHeight()
                                    .height(200.dp),
                                contentScale = ContentScale.FillHeight,
                            )

                        }



                    }

                }



            }

        }



    }



    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        BaseballApplicationTheme {
            MainMenu()
        }
    }


}









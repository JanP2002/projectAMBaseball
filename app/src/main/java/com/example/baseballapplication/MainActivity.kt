package com.example.baseballapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.baseballapplication.ui.theme.BaseballApplicationTheme
import org.json.JSONArray

class MainActivity : ComponentActivity() {

    val sheetId = "1pYb2Di5YoF0IbpV0kq1D25J_Vv-JGPWu7_yE0kAxFS0"
    val apiKEY = "AIzaSyCfkvR4qvJbbL7ObotNmf68XA1Ghc4abnM"

    val players = ArrayList<PlayersModel>()

    var jsonArray = JSONArray()

    fun nameFormatter(number: String, name: String) : String {
        return "#$number $name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = "https://sheets.googleapis.com/v4/spreadsheets/"+sheetId+"/values/BAT?key="+apiKEY
        val queue = Volley.newRequestQueue(this)
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
                    val g = json.getString(2)
                    val pa = json.getString(3)
                    val rbi = json.getString(12)
                    val ops = json.getString(36)


                    val player = PlayersModel(nameFormatter(strNumber,strName),
                        "$g G", "$pa PA", "$rbi RBI", "$ops OPS",R.drawable.player)
                    players.add(player)
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
        queue.add(jsonObjectRequest)
        setContent {
            BaseballApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
                MainMenu()
            }
        }
    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> val data = result.data
        //Toast.makeText(this, data?.getStringExtra("tag"), Toast.LENGTH_SHORT).show()


    }

    private fun showTeams() {
        val myIntent = Intent(this, TeamsActivity::class.java)
        //myIntent.putParcelableArrayListExtra("players",players)
        resultLauncher.launch(myIntent)

    }


    private fun showStadiums() {
        val myIntent = Intent(this, StadiumsActivity::class.java)
        //myIntent.putParcelableArrayListExtra("players",players)
        resultLauncher.launch(myIntent)

    }

    fun showPlayers() {

//        val bundle = Bundle()
//        bundle.putParcelableArrayList("players",players)

        val intent = Intent(this, PlayersActivity::class.java)
//        intent.putExtra("bundle",bundle)
//        intent.putExtra("myWorth",0)
        intent.putParcelableArrayListExtra("players",players)
        startActivity(intent);

//        val intent = Intent(this, TestActivity::class.java)
//        startActivity(intent);

    }


    @Composable
    fun MainMenu() {
        //var text by remember { mutableStateOf("") }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 30.dp, vertical = 200.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceEvenly
//
//        ) {
//
//            Button(
//                onClick = { showPlayers() },
//            ) {
//                Text(text = "Zobacz Graczy")
//            }
//
//            Button(
//                onClick = { showTeams() },
//            ) {
//                Text(text = "Zobacz Druzyny")
//            }
//
//
//            Button(
//                onClick = { showStadiums() },
//            ) {
//                Text(text = "Zobacz Stadiony")
//            }
//        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp)
        
        ) {
            item {
                Box(modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Cyan)
                    .clickable(onClick = {
                        showPlayers()
                    }),
                    contentAlignment = Alignment.Center,

                    ) {

                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.player),
                            contentDescription = "My Image",
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .height(50.dp)
                        )


                        Box(contentAlignment = Alignment.Center,)
                        {
                            Text(
                                text = "Gracze",
                                modifier = Modifier.padding(16.dp).fillMaxHeight(),
                                style = TextStyle(color = Color.Black, fontSize = 24.sp),
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
                    .background(Color.Cyan)
                    .clickable(onClick = {
                        showTeams()
                    }),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Drużyny",
                        modifier = Modifier.padding(16.dp),
                        style = TextStyle(color = Color.Black, fontSize = 24.sp)
                    )

                }

            }


            item {
                Box(modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Cyan)
                    .clickable(onClick = {
                        showStadiums()
                    }),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Stadiony",
                        modifier = Modifier.padding(16.dp),
                        style = TextStyle(color = Color.Black, fontSize = 24.sp)
                    )

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









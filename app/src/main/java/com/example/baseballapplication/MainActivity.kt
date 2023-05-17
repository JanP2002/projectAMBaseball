package com.example.baseballapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            result -> val data = result.data
//        //Toast.makeText(this, data?.getStringExtra("tag"), Toast.LENGTH_SHORT).show()
//
//
//    }

//    private fun onClick() {
//        val myIntent = Intent(this, PlayersActivity::class.java)
//        myIntent.putParcelableArrayListExtra("players",players)
//        resultLauncher.launch(myIntent)
//
//    }

    fun playersIntent() {

//        val bundle = Bundle()
//        bundle.putParcelableArrayList("players",players)

        val intent = Intent(this, PlayersActivity::class.java)
//        intent.putExtra("bundle",bundle)
//        intent.putExtra("myWorth",0)
        intent.putParcelableArrayListExtra("players",players)
        startActivity(intent);
    }


    @Composable
    fun MainMenu() {
        //var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            Button(
                onClick = { playersIntent() },
            ) {
                Text(text = "Gracze ")
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









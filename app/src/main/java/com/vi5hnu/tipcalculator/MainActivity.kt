package com.vi5hnu.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vi5hnu.tipcalculator.components.InputField
import com.vi5hnu.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val totalBillState= remember {
                    mutableStateOf("0")
                }
                val personCountState= remember {
                    mutableStateOf<Int>(1)
                }
                val sliderState= remember {
                    mutableStateOf(0f)
                }
                Column(modifier = Modifier.padding(25.dp)) {
                    PriceContainer(if(totalBillState.value.toDoubleOrNull()!=null) totalBillState.value.toDouble()/personCountState.value+((sliderState.value*totalBillState.value.toDouble()).roundToLong())/personCountState.value else 0.0)
                    Spacer(modifier = Modifier.height(10.dp))
                    BillForm(totalBillState=totalBillState,personCount=personCountState,sliderState=sliderState)
                }
            }
        }
    }
}

@Composable
fun MyApp(content:@Composable ()->Unit){
    TipCalculatorTheme(dynamicColor = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Box{
                content()
            }
        }
    }
}

@Composable
fun PriceContainer(price:Double=0.0){
    val montserrat= FontFamily(
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp, topEnd = 2.dp, bottomStart = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(0.3f)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Total Per Person", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, fontFamily = montserrat))
            Text(text = "₹ ${"%.2f".format(price)}", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = montserrat))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    totalBillState:MutableState<String>,
    personCount:MutableState<Int>,
    sliderState:MutableState<Float>,
    modifier: Modifier=Modifier,onValueChange:(String)->Unit={}){

    val isValidState= remember(totalBillState.value) {
        totalBillState.value.toDoubleOrNull()!=null && totalBillState.value.toDouble()>0
    }
    val keyboradController=LocalSoftwareKeyboardController.current
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier=Modifier.padding(7.dp)) {
            InputField(
                valuState =totalBillState ,
                labelText ="Enter Bill" ,
                enabled = true,
                isSingleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onAction = KeyboardActions {
                    if(!isValidState) return@KeyboardActions;
                    onValueChange(totalBillState.value.trim())
                    keyboradController?.hide()
                })
            if(isValidState){
                Column(modifier = Modifier.padding(12.dp)){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Split",
                            fontSize = 24.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                modifier = Modifier.size(45.dp),
                                shape = CircleShape,
                                enabled = personCount.value>1,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(0.7f)),
                                onClick = { personCount.value-- }) {
                                Icon(imageVector = ImageVector.vectorResource(R.drawable.minus), contentDescription = "minus icon" )
                            }
                            Text(text = personCount.value.toString(),modifier=Modifier
                                .padding(horizontal = 7.dp),
                                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                            )
                            Button(
                                modifier = Modifier.size(45.dp),
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(0.7f)),
                                onClick = { personCount.value++ }) {
                                Icon(imageVector = ImageVector.vectorResource(R.drawable.add), contentDescription = "minus icon" )
                            }
                        }
                    }
                    Divider(modifier=Modifier.padding(horizontal = 7.dp, vertical = 15.dp), thickness = 1.dp)
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Tip", fontSize = 24.sp)
                        Text(text = "₹ ${(sliderState.value*totalBillState.value.toDouble()).roundToLong()}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Text(text = "${"%.1f".format(sliderState.value*100)}%",
                        modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Slider(
                        value =sliderState.value ,
                        onValueChange ={sliderState.value=it},
                        modifier=Modifier,
                        )
                }

            }
        }
    }
}
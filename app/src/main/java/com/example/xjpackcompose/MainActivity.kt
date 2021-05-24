package com.example.xjpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            XJpackComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android, jack and jill! went up the hill!")
//                }
//            }

            //  2. rows, columns
//            RowsColumnsDemo()
            //  3. modifiers
//            ModifiersDemo()
            //  4. create image card
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(.5f)
//                    .padding(16.dp)
//            ) {
//                ImgCardView(
//                    painter = painterResource(id = R.drawable.kermit2),
//                    contentDescription = "kermit the frog!",
//                    title = "kermit the frog!"
//                )
//            }
            //  5. styling text
//            StylingText()

            //  6. State - 1
//            UIStateInCompose(modifier = Modifier.fillMaxSize())
            //  6. State - 2 - changing state of another box(view) based on click on first view
//            Column {
//                val color = remember {
//                    mutableStateOf(Color.Yellow)
//                }
//                UIStateInCompose2(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxSize()
//                ) {
//                    color.value = it
//                }
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(color = color.value)
//                        .fillMaxSize()
//                )
//            }

            //  7. Textfields, Buttons & Showing Snackbars
//            ComposeSnackBarInScaffold()

            //  8. Lists
//            ComposeAList()
//            ComposeALazyList()

            //  9. ConstraintLayout
//            ComposeAConstraintLayout()

            //  10. Side Effects & Effect Handlers
            SideEffectDemo()
        }
    }

    //  10. Side Effects & Effect Handlers - https://www.youtube.com/watch?v=f_iIMscTNjQ&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=12
    @Composable
    private fun SideEffectDemo() {
//        SimpleSideEffect()
//        CleanUpDisposableEffect()
//        LaunchEffectDemo()
        ProduceStateDemo()
    }

    private var i = 0

    @Composable
    private fun SimpleSideEffect() {
        SideEffect {    //  gets called when everytime composition is successful, not executed when it fails
            i++
        }
        Button(onClick = {
            println("yup: counter is : $i")
        }) {
            Text(text = "click me!")
        }
    }

    @Composable
    private fun CleanUpDisposableEffect(backPressedDispatcher: OnBackPressedDispatcher? = null) {
        val backPressCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //  do something
                }
            }
        }

        DisposableEffect(key1 = backPressedDispatcher) {
            backPressedDispatcher?.addCallback(backPressCallback)
            onDispose { //  free ups memory, on every recomposition
                backPressCallback.remove()
            }
        }
    }

    @Composable
    private fun LaunchEffectDemo() {
        val scaffoldState = rememberScaffoldState()
        val cScope = rememberCoroutineScope()

        Scaffold(scaffoldState = scaffoldState) {
            var counter by remember {
                mutableStateOf(0)
            }

            //  showing snackbar when counter is divisible by 5
//            if (counter % 5 == 0 && counter > 0)
//                cScope.launch { scaffoldState.snackbarHostState.showSnackbar("Hello $counter") }    //  this'll queue up snackbars and displayed, when button is repeatedly pressed - issue

            //  to overcome above queue up issue, since prev coroutine is cancelled every time recomposed
            if (counter % 5 == 0 && counter > 0) {
                LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar("Hello $counter")
                }
            }

            Button(onClick = { counter++ }) {
                Text(text = "click me : $counter")
            }
        }
    }

    //  something similar will be used for n/w calls, db access in another thread and update UI when result is received
    @Composable
    private fun ProduceStateDemo() {
        val scaffoldState = rememberScaffoldState()

        Scaffold(scaffoldState = scaffoldState) {
            val counter = produceState(initialValue = 0) {
                delay(3000L)    //  simulating n/w call
                value = 24
            }

            //  to overcome above queue up issue, since prev coroutine is cancelled every time recomposed
            if (counter.value % 5 == 0 && counter.value > 0) {
                LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar("Hello ${counter.value}")
                }
            }

            Button(onClick = { }) {
                Text(text = "click me : ${counter.value}")
            }
        }
    }

    //  9. ConstraintLayout: https://www.youtube.com/watch?v=FBpiOAiseD0&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=9
    @Composable
    private fun ComposeAConstraintLayout() {
        val constraintSet = ConstraintSet {
            val gBox = createRefFor("gBox")
            val rBox = createRefFor("rBox")

//            3
//            val guildLine = createGuidelineFromTop(.5f)


            constrain(gBox) {
                top.linkTo(parent.top)
//                3
//                top.linkTo(guildLine)
                start.linkTo(parent.start)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)    //  Dimension.fillToConstraints => 0dp
            }

            constrain(rBox) {
                top.linkTo(gBox.top)
                start.linkTo(gBox.end)
//                1
//                end.linkTo(parent.end)
//                width = Dimension.fillToConstraints
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)    //  Dimension.fillToConstraints => 0dp
            }

//            2
//            createHorizontalChain(gBox, rBox, chainStyle = ChainStyle.Packed)
        }

        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .layoutId("gBox")
            )
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .layoutId("rBox")
            )
        }
    }

    //  8. Lists - https://www.youtube.com/watch?v=1Thp0bB5Ev0&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=8
    @Composable
    private fun ComposeALazyList() {
//        LazyColumn {
//            items(5000) {
//                Text(
//                    text = "Item $it",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(24.dp),
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }

        LazyColumn {
            itemsIndexed(
//                listOf("Jack", "and", "jill")
                listOf(3, 5, 6, 788, 9993, 5, 6, 788, 9993, 5, 6, 788, 9993, 5, 6, 788, 999)
            ) { index, item ->
                Text(
                    text = "item @ $index : $item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    //  8. Lists - https://www.youtube.com/watch?v=1Thp0bB5Ev0&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=8
    @Composable
    private fun ComposeAList() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            for (index in 1..50) {
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    //  7. Textfields, Buttons & Showing Snackbars - https://www.youtube.com/watch?v=_yON9d9if6g&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=7
    @Composable
    private fun ComposeSnackBarInScaffold() {
        val scaffoldState = rememberScaffoldState()
        var textFieldState by remember {
            mutableStateOf("")
        }
        val cScope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                TextField(
                    value = textFieldState,
                    label = {
                        Text("Enter you name")
                    },
                    onValueChange = {
                        textFieldState = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)   //  button is not moving to right/end - check it later
                ) {
                    Button(
                        onClick = {
                            cScope.launch { scaffoldState.snackbarHostState.showSnackbar("Hello $textFieldState!") }
                        }) {
                        Text(text = "Pls, greet me!")
                    }
                }

            }
        }
    }

    //  6. State: https://www.youtube.com/watch?v=s3m1PSd7VWc&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=6
    @Composable
    private fun UIStateInCompose(modifier: Modifier = Modifier) {
        val color = remember {
            mutableStateOf(Color.Yellow)
        }
        Box(
            modifier = modifier
                .background(color = color.value)
                .clickable {
                    color.value =
                        Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        )
                }
        )
    }

    @Composable
    private fun UIStateInCompose2(
        modifier: Modifier = Modifier,
        callback: (Color) -> Unit
    ) {
        val color = remember {
            mutableStateOf(Color.Yellow)
        }
        Box(
            modifier = modifier
                .background(color = color.value)
                .clickable {
                    callback.invoke(
                        Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        )
                    )
                }
        )
    }

    //  5. styling text : https://youtu.be/nm_LNJWHi9A?list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC
    private val fontFamily = FontFamily(
        Font(R.font.lato_thin, FontWeight.Thin),
        Font(R.font.lato_light, FontWeight.Light),
        Font(R.font.lato_regular, FontWeight.Normal),
        Font(R.font.lato_black, FontWeight.Medium),
        Font(R.font.lato_bold, FontWeight.Bold)
    )

    @Composable
    private fun StylingText() {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xff101010))
//        ) {
//            Text(
//                text = "jack and jill!",
//                color = Color.White,
//                fontSize = 30.sp,
//                fontFamily = fontFamily,
//                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
//                textAlign = TextAlign.Center,
//                textDecoration = TextDecoration.LineThrough
//            )
//        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff101010))
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 50.sp
                        )
                    ) {
                        append("M")
                    }
                    append("ohammed")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 50.sp
                        )
                    ) {
                        append("A")
                    }
                    append("udhil")
                },
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        }
    }

    //  4. create image card : https://www.youtube.com/watch?v=KPVoQjwmWX4&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=4
    @Composable
    private fun ImgCardView(
        painter: Painter,
        contentDescription: String,
        title: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )

                //  gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 300f
                            )
                        )
                )

                Text(
                    text = title,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }

    //  2. rows, columns - https://www.youtube.com/watch?v=rHKeRWK3zL4&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=2
    @Composable
    private fun RowsColumnsDemo() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello")
            Text(text = "World")
            Text(text = "Jetpack Compose")
        }

        Row(
            modifier = Modifier
                .fillMaxSize(.75f)
//                    .fillMaxSize()
                .background(Color.Cyan),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hello")
            Text(text = "World")
            Text(text = "Jetpack Compose!")
        }

        Row(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .background(Color.Magenta),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hello")
            Text(text = "World")
            Text(text = "Jetpack Compose!")
        }


        Row(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight(.45f)
                .background(Color.Red),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hello")
            Text(text = "World")
            Text(text = "Jetpack Compose!")
        }
    }

    //  3. modifiers - https://www.youtube.com/watch?v=XCuC_p3E0qo&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=3
    @Composable
    private fun ModifiersDemo() {
//        Column(
//            modifier = Modifier
//                .background(Color.Cyan)
//                .fillMaxHeight(.5f)
//                .fillMaxWidth()
//                .padding(top = 50.dp)
////                .requiredWidth(600.dp)
//        ) {
//            Text(text = "Hello", modifier = Modifier.offset(10.dp, 30.dp))
//            Spacer(modifier = Modifier.height(50.dp))
//            Text(text = "World")
//        }

        Column(
            modifier = Modifier
                .background(Color.Cyan)
                .fillMaxSize()
                .border(5.dp, Color.Magenta)
                .padding(5.dp)
                .border(5.dp, Color.Green)
                .padding(5.dp)
                .border(10.dp, Color.Black)
                .padding(10.dp)
        ) {
            Text(
                text = "Hello", modifier = Modifier
                    .border(5.dp, Color.Yellow)
                    .padding(5.dp)
                    .offset(20.dp, 20.dp)
                    .border(10.dp, Color.Black)
                    .padding(10.dp)
                    .clickable {
                        println("yup: \"Hello\" got clicked!")
                    }
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "World")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    XJpackComposeTheme {
//        Greeting("Android, Jack and jill, hi")
//    }
//}
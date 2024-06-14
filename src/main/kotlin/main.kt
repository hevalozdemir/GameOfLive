
fun main() {
   var feld  = initFeld(5, 5)


    while (true) {
        Thread.sleep(2000)
        feld = play(feld)
        feld.printFeld()
    }




}

/*
* 1 0 1
* 0 1 0
* 1 0 0
* */

/*
* 0 1 0
* 0 1 0
* 1 0 0
* */

fun copyArray(feld: spielfeld): spielfeld{
    val kopie = feld.copyOf()
    return kopie.mapIndexed{ i,a -> feld[i].copyOf() }.toTypedArray()
}


fun play(feld: spielfeld): spielfeld{
    val height = feld.size
    val width = feld.size
    // Kopie von Spielfeld erzeugen
    val kopie = copyArray(feld)
    // alle Felder der Kopie betrachten und in das richtige Spielfeld eintragen


    for (x in 0.. width- 1) {
        for (y in 0..height - 1) {
            val leben: Int = find1count(findNachbarn(x, y, kopie))  //Anzahl der lebenden Nachbarn
            if ((leben < 2) && kopie[x][y] == 1) {
                feld[x][y] = 0
            }
            if (leben in 2..3 && kopie[x][y] == 1) {
                feld[x][y] = 1
            }
            if (leben > 3 && kopie[x][y] == 1) {
                feld[x][y] = 0
            }
            if (leben == 3 && kopie[x][y] == 0) {
                feld[x][y] = 1
            }
        }
    }
    return feld
}

fun find1count(list: List<Int>) :Int {
    val result =list.count{ it == 1}
    //println("Count1: $result")
    return result
    }

fun findNachbarn(x: Int, y: Int, feld: spielfeld): List<Int> {
    var listofNachbarn: List<Int> = emptyList()
    val height = feld[0].size //y
    val width = feld.size  //x
    if(x>0 && x<width-1 && y>0 && y<height-1){
        listofNachbarn = listOf(feld[x][y-1], feld[x] [y+1], feld[x-1][y-1], feld[x-1][y+1], feld[x+1][y-1], feld[x+1][y+1], feld[x-1][y], feld[x+1][y])
    }
    else if(x>0 && x<width-1 && y==0){
        listofNachbarn = listOf(feld[x][y+1], feld[x-1][y+1], feld[x+1][y+1], feld[x-1][y], feld[x+1][y])
    }
    else if(x == width-1 && y>0 && y<height-1){
        listofNachbarn = listOf(feld[x][y-1], feld[x] [y+1], feld[x-1][y-1], feld[x-1][y+1], feld[x-1][y])

    }
    else if (x>0 && x<width-1 && y ==height-1){
        listofNachbarn = listOf(feld[x][y-1], feld[x-1][y-1], feld[x+1][y-1], feld[x-1][y], feld[x+1][y])

    }
    else if(x== 0 && y>0 && y<height-1){
        listofNachbarn = listOf(feld[x][y-1], feld[x][y+1], feld[x+1][y-1], feld[x+1][y+1], feld[x+1][y])
    }
    else if(x==0 && y==0){
        listofNachbarn = listOf(feld[x][y+1], feld[x+1][y+1], feld[x+1][y])
    }
    else if(x==width-1 && y==height-1){
        listofNachbarn =  listOf(feld[x][y-1], feld[x-1][y-1], feld[x-1][y])
    }
    else if(x==0 && y==height-1){
        listofNachbarn =  listOf(feld[x][y-1], feld[x+1][y-1], feld[x+1][y])
    }
    else if(x==width-1 && y==0){
        listofNachbarn =  listOf(feld[x][y+1], feld[x-1][y+1], feld[x-1][y])
    }
    //println("($x, $y) Nachbarn: $listofNachbarn")
     return listofNachbarn
}


typealias spielfeld = Array<Array<Int>>

fun spielfeld.printFeld(){
    print("\n".repeat(20))
    this.forEachIndexed { index, ints ->
        ints.forEachIndexed { index2, i ->
            val feld = this[index][index2]
            if(feld == 0){
                print("_")
            } else {
                print("#")
            }
        }

        println()
    }
}

fun initFeld(height: Int, width: Int): spielfeld{
    val array = arrayOfNulls<Int?>(width)

    val feld: Array<Array<Int?>> = arrayOfNulls<Array<Int?>>(height).map { array }.toTypedArray()

    for (i in 0..height - 1) {
        val row: Array<Int?> = arrayOfNulls(width)
        for (e in 0..width - 1) {
            row[e] = (0..1).random()
        }
        feld[i] = row
    }

    return feld as spielfeld
}
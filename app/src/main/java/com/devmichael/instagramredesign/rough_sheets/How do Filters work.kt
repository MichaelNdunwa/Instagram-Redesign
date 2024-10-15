package com.devmichael.instagramredesign.rough_sheets

fun main() {
    val names = listOf("Michael", "Amos", "Kaycee", "Joshua", "Francis", "Frank")
    val fourLetterNames = names.filter { it.length == 4 }
    val greaterThanFourLetterNames = names.filter { it.length > 4 }
    println("fourLetterNames: $fourLetterNames")
    println("greaterThanFourLetterNames: $greaterThanFourLetterNames")
}
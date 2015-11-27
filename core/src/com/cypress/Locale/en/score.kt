package com.cypress.Locale.en

/** Returns string with score info on English. */
public fun score(data : Array<Int>) : String {
    val text =
            "Level score       ${data[0]}\n" +
            "Player deaths     ${data[1]}\n" +
            "Enemies killed    ${data[2]}\n" +
            "Shots fired        ${data[3]}\n" +
            "Shots hit          ${data[4]}\n" +
            "Accuracy          ${data[5]}%"
    return text
}

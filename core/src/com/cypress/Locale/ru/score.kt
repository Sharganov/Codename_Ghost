package com.cypress.Locale.ru

/** Returns string with score info on English. */
public fun score(data : Array<Int>) : String {
    val text =
            "Заработано очков      ${data[0]}\n" +
            "Смертей                ${data[1]}\n" +
            "Убито врагов          ${data[2]}\n" +
            "Сделано выстрелов    ${data[3]}\n" +
            "Попаданий             ${data[4]}\n" +
            "Точность               ${data[5]}%"
    return text
}

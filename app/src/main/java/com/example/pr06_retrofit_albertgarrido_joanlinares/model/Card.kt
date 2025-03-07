package com.example.pr06_retrofit_albertgarrido_joanlinares.model

data class Card(
    val id: String,
    val name: String,
    val supertype: String?,
    val subtypes: List<String>?,
    val hp: String?,
    val types: List<String>?,
    val images: Images,
    val cardmarket: CardMarket?,
    val tcgplayer: Tcgplayer?

){
    val marketPrice: Double
        get() = cardmarket?.prices?.averageSellPrice ?: tcgplayer?.prices?.normal?.market ?: 0.0


}

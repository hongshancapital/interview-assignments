//
//  ApplicationItem.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import Foundation

struct ApplicationItem: Identifiable, Decodable {
    var id         : Int
    var iconUrl    : String
    var name       : String
    var description: String
    var isFavorite : Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id          = "trackId"
        case iconUrl     = "artworkUrl60"
        case name        = "trackName"
        case description = "description"
    }
}

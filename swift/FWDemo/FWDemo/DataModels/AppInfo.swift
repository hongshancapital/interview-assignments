//
//  AppInfo.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation

struct AppInfo : Identifiable, Decodable {
    
    var id : Int
    var thumbUrl : String
    var name : String
    var description : String?
    var isFavorite = false
    
    enum CodingKeys : String, CodingKey {
        case id = "trackId"
        case thumbUrl = "artworkUrl60"
        case name = "trackName"
        case description
    }
}

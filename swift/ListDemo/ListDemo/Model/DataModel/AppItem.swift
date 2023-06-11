//
//  AppItem.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//


import SwiftUI

struct AppItem: Identifiable, Codable {
    var id: Int
    var iconUrl: String
    var name: String
    var description: String
    var isFavorite: Bool = false
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case iconUrl = "artworkUrl60"
        case name = "trackName"
        case description = "description"
    }
}

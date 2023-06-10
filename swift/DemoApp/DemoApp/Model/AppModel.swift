//
//  AppModel.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation
import SwiftUI

struct AppModel: Identifiable, Decodable {
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

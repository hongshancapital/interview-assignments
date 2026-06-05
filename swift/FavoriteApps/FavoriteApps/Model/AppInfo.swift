//
//  AppInfo.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import Foundation
import SwiftUI

struct AppInfo: Codable, Identifiable {
    var id = UUID()
    
    let trackId: Int
    let description: String
    let trackName: String
    let artworkUrl60: String
    
    var isFavorite: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case trackId
        case description
        case trackName
        case artworkUrl60
    }
}

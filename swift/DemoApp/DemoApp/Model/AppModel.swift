//
//  AppModel.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation
import SwiftUI

class AppModel: Identifiable, ObservableObject, Decodable {
    var id: Int
    var iconUrl: String
    var name: String
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case iconUrl = "artworkUrl60"
        case name = "trackName"
        case description = "description"
    }
    
    @Published var isFavorite: Bool = false {
        didSet {
            FavoriteManager.shared.setFavorite(isFavorite: isFavorite, by: id)
        }
    }

    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        id = try container.decode(Int.self, forKey: .id)
        iconUrl = try container.decode(String.self, forKey: .iconUrl)
        name = try container.decode(String.self, forKey: .name)
        description = try container.decode(String.self, forKey: .description)
        isFavorite = FavoriteManager.shared.isFavorite(by: id)
    }
}

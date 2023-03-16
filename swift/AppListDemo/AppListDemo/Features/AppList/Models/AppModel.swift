//
//  AppModel.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import Foundation

struct AppModel: Codable, Identifiable {
    let id: String
    let appName: String
    let appDescription: String
    let appIconUrlString: String
    var isFavourite: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "bundleId"
        case appName = "trackName"
        case appDescription = "description"
        case appIconUrlString = "artworkUrl100"
    }
}

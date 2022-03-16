//
//  AppInfoModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/9.
//

import SwiftUI

/// App 数据实体
struct AppInfoModel: Codable, Identifiable {
    let id = UUID()
    let name: String
    let description: String
    let imageUrlString: String
    
    var isCollected = false
    
    private enum CodingKeys: String, CodingKey {
        case name = "artistName"
        case imageUrlString = "artworkUrl100"
        case description
    }
}

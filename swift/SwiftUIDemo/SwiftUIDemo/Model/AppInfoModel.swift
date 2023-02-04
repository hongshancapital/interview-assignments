//
//  AppInfoModel.swift
//  Demo
//
//  Created by hbc on 2022/2/18.
//  Copyright © 2022 hbc. All rights reserved.
//

import Foundation

struct ResourceData: Codable {
    var resultCount: Int
    var results: [AppInfo]
    
    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}

struct AppInfo: Codable,Identifiable {
    var id = UUID()
    let imageUrl: String
    let name: String
    let description: String
    
    enum CodingKeys: String, CodingKey {
        case imageUrl = "artworkUrl60"
        case name = "trackCensoredName"
        case description
    }
    
}




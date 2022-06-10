//
//  AppInfo.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import Foundation

struct AppInfo: Codable, Hashable, Identifiable {
    var id: Int
    
    var trackName: String
    var artworkUrl60: String
    var description: String
}

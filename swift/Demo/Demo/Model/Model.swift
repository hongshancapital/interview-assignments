//
//  Model.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/5.
//

import Foundation
import SwiftUI

struct AppInfo: Identifiable, Decodable , Equatable{
    
    var id: Int = 0
    var iconUrl = ""
    var name = ""
    var description = ""
    var isLiked = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case iconUrl = "artworkUrl60"
        case name = "trackName"
        case description = "description"
    }
}

struct AppInfoData: Decodable {
    
    var resultCount: Int = 0
    var results = [AppInfo]()
}

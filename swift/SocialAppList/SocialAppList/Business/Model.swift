//
//  Model.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import KakaJSON

struct SocialAppResponse: Convertible {
    var resultCount: Int = 0
    var results: [SocialApp] = []
}

struct SocialApp: Convertible, Identifiable {
    var id: String = ""
    var trackName: String = ""
    var description: String = ""
    var artworkUrl60: String = ""
    var isLiked: Bool = false
        
    func kj_modelKey(from property: Property) -> ModelPropertyKey {
        switch property.name {
        case "id": return "bundleId"
        default: return property.name
        }
    }
}

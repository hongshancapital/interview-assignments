//
//  AppListModel.swift
//  AppList
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import Foundation
import Combine

struct ResultInfo: Codable {
    var resultCount: Int
    var results: [AppInfo]?
}

struct AppInfo: Codable, Identifiable {
    let icon: String
    let name: String
    let description: String
    let id: Int
    var like = false
    
    private enum CodingKeys: String, CodingKey {
        case icon = "artworkUrl60"
        case name = "trackName"
        case description = "description"
        case id = "trackId"
    }
}

struct AppListModel {
    var infoArray: [AppInfo] = []
    var likedItems: Set<Int> = []  // 保存已点赞的 trackId
    
    mutating func addAppInfos(_ arr: [AppInfo]) {
        for info in arr {
            var temp = info
            temp.like = likedItems.contains(info.id)
            infoArray.append(temp)
        }
    }
    
    mutating func toggleLike(_ id: Int) {
        guard let index = infoArray.indices.firstIndex(where: { infoArray[$0].id == id }) else {
            return
        }
        infoArray[index].like.toggle()
        if infoArray[index].like {
            likedItems.insert(id)
        } else {
            likedItems.remove(id)
        }
    }
    
    mutating func removeAll() {
        infoArray.removeAll()
    }
}

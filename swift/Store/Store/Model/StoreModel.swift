//
//  StoreModel.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/1.
//

import Foundation

struct StoreModel {
    var apps: [AppModel] = []
    var likes: Set<Int> = []
    
    mutating func addAppModels(_ models: [AppModel]) {
        for model in models {
            var model = model
            model.like = likes.contains(model.trackId)
            apps.append(model)
        }
    }
    
    mutating func like(_ id: Int) {
        if let index = indexOfApp(id) {
            apps[index].like.toggle()
            if apps[index].like {
                likes.insert(apps[index].trackId)
            } else {
                likes.remove(apps[index].trackId)
            }
        }
    }
    
    mutating func clearModels() {
        apps.removeAll()
    }
    
    private func indexOfApp(_ id: Int) -> Int? {
        apps.indices.first { apps[$0].trackId == id }
    }
}

struct AppModel: Codable {
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl60: String
    var like = false
    
    private enum CodingKeys: String, CodingKey {
        case trackId
        case trackName
        case description
        case artworkUrl60
    }
}

struct SearchModel: Codable {
    let results: [AppModel]
}

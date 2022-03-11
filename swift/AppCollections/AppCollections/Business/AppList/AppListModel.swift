//
//  AppListModel.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/11.
//

import Foundation

struct AppListModel: Decodable {
    let resultCount: Int
    let results: [Item]
    
    struct Item: Decodable {
        let trackId: Int
        let trackName: String
        let description: String
        let artworkUrl60: URL
    }
}

extension AppListModel.Item: Identifiable {
    var id: String { "\(trackId)" }
}

//
//  AppListModel.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/11.
//

import Foundation

// MARK: - HTTP Response Model

struct AppListResponseModel: Decodable {
    let resultCount: Int
    let results: [App]

    struct App: Decodable {
        let trackId: Int
        let trackName: String
        let description: String
        let artworkUrl60: URL
    }
}

// MARK: - UI Model

struct AppModel {
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl60: URL
    var isFavorite: Bool
}

extension AppModel: Identifiable {
    var id: Int { trackId }
}

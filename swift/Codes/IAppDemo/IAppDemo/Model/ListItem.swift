//
//  AppListModel.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation

struct ListItemModel : Identifiable , Decodable {
    var id: Int { trackId }
    var isFavorite: Bool? = false
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl60: URL
}

struct AppListResponseModel: Decodable {
    let resultCount: Int
    let results: [ListItemModel]
}

//
//  AppListModel.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI

struct AppModel: Codable {
    @AppListDefault<Int>var resultCount: Int
    @AppListDefault<[AppListModel]>var results: [AppListModel]
}


struct AppListModel: Codable, Identifiable, Equatable {
    var id: Int {
        return trackId
    }

    @AppListDefault<Int> var trackId: Int
    @AppListDefault<String> var trackName: String
    @AppListDefault<URL> var artworkUrl60: URL?
    @AppListDefault<String> var description: String
    @AppListDefault<Bool.False> var isFavorite: Bool
    
    static func == (lhs: AppListModel, rhs: AppListModel) -> Bool {
        lhs.trackId == rhs.trackId
    }
}

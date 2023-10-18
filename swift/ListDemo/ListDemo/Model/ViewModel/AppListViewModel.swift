//
//  AppListViewModel.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/10.
//

import Combine
import SwiftUI

// May be nedd to do sth about business, so use ViewModel
struct AppListViewModel: Identifiable, Codable {
    
    let app: AppItem
    init(app: AppItem) {
        self.app = app
    }
    
    
    var id: Int { app.id }
    var name: String { app.name }
    var descriptionText: String { app.description }
    var isFavorite: Bool { return app.isFavorite }
    var iconImageURL: URL {
        URL(string: app.iconUrl)!
    }
}

extension AppListViewModel: CustomStringConvertible {
    var description: String {
        "AppListViewModel - \(id) - \(self.name)"
    }
}

extension AppListViewModel: Equatable {
    static func == (lhs: AppListViewModel, rhs: AppListViewModel) -> Bool {
        return lhs.id == rhs.id && lhs.name == rhs.name
    }
}

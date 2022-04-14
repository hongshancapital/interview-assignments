//
//  DataViewModel.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation
import Combine

@MainActor class DataViewModel: ObservableObject {

    @Published var apps = [Entity]()

    var favoriteApps = [String]()

    @Published var isEnd: Bool = false

    let service: AppService

    init(_ service: AppService) {
        self.service = service
    }

    func refresh() async throws {
        self.apps = try await service.refresh()
        self.refreshFavoriteLikes()
        self.isEnd = false
    }

    func loadMore() async throws {
        self.apps = try await service.loadMore()
        self.refreshFavoriteLikes()
        self.isEnd = true
    }

    func like(entity: Entity, favorite: Bool) {
        favoriteApps = favoriteApps.filter {$0 != entity.bundleID}
        if favorite {
            favoriteApps.append(entity.bundleID)
        }
        self.refreshFavoriteLikes()
    }

    func refreshFavoriteLikes() {
        self.apps = self.apps.map({[unowned self] entity in
            var en = entity
            en.favorite = self.favoriteApps.contains(en.bundleID)
            return en
        })
    }

}

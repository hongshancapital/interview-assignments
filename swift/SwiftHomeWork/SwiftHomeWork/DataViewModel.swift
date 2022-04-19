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
    
    var infoMap = [UUID:Int]()

    @Published var isEnd: Bool = false

    let service: AppService

    init(_ service: AppService) {
        self.service = service
    }
    
    init(abb  service: AppService) {
        self.service = service
    }

    func refresh() async throws {
        let items = try await service.refresh()
        self.apps = self.refreshFavoriteLikes(items)
        
        self.infoMap = self.apps.enumerated().reduce([UUID:Int]()) { partialResult, entity in
            var c = partialResult
            c[entity.element.id] = entity.offset
            return c
        }
        
        self.isEnd = false
    }

    func loadMore() async throws {
        let (entities, isEnd ) = try await service.loadMore()
        self.isEnd = isEnd
        
        let filtered = entities.filter {entity in
            if let index = infoMap[entity.id] {
                apps[index] = entity
                return true
            }
            return false
        }
       
        self.apps.append(contentsOf: self.refreshFavoriteLikes(filtered))
        //todo: refreshfavorite是否可以降低时间复杂度
        for element in filtered.enumerated() {
            self.infoMap[element.element.id] = element.offset + self.apps.count
        }
    }

    func like(entity: Entity, favorite: Bool) {
        favoriteApps = favoriteApps.filter {$0 != entity.bundleID}
        if favorite {
            favoriteApps.append(entity.bundleID)
        }
        if let index = self.apps.firstIndex(where: {$0.id == entity.id}){
            self.apps[index].favorite = !self.apps[index].favorite
        }
    }

    func refreshFavoriteLikes(_ items:[Entity])->[Entity] {
        return items.map({[unowned self] entity in
            var en = entity
            en.favorite = self.favoriteApps.contains(en.bundleID)
            return en
        })
    }

}

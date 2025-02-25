//
//  AppListViewModel.swift
//  SCDT-Demo
//
//  Created by wuzhe on 12/12/22.
//

import Foundation
import Combine


class AppListViewModel: ObservableObject{
    @Published private(set) var appInfomations: [AppInfomation] = []
    @Published public var hasMore = true
    @Published public var error: NetApiError?
    
    var cancellables = Set<AnyCancellable>()
    
    public var currentPage = 0
    
    func reload() {
        print("reload data")
        self.error = nil
        cancellables.forEach { $0.cancel()}
        
        NetApi.shared.fetchPage(1)
            .receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion{
                case .failure(let err):
                    self.error = err as? NetApiError
                case .finished:
                    print("reload data finished")
                }
            } receiveValue: { result in
                self.hasMore = result.hasMore
                self.currentPage = result.page
                self.appInfomations = result.results
            }
            .store(in: &cancellables)
    }
    
    func loadMore() {
        print("load more data")
        self.error = nil
        NetApi.shared.fetchPage(currentPage + 1)
            .receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion{
                case .failure(let err):
                    self.error = err as? NetApiError
                case .finished:
                    print("load page data finished")
                }
            } receiveValue: { result in
                self.appInfomations += result.results
                self.hasMore = result.hasMore
                self.currentPage = result.page
            }
            .store(in: &cancellables)
    }
}

struct AppInfomation: Codable, Hashable, Identifiable{
    let id: Int
    let title: String
    let subtitle: String
    let thumbnail: String
    var favorited: Bool = false
    
    enum CodingKeys: String, CodingKey{
        case id = "trackId"
        case title = "trackName"
        case subtitle = "description"
        case thumbnail = "artworkUrl512"
    }
}

class FavoriteAppManager {
    private var favoritedAppIds : Set<Int> = Set()
    
    static let shared = FavoriteAppManager()
    
    func hasFavorited(_ appId: Int) -> Bool{
        return favoritedAppIds.contains(appId)
    }
    
    func favorite(_ appId: Int, favorited: Bool){
        if favorited{
            favoritedAppIds.insert(appId)
        }
        else{
            favoritedAppIds.remove(appId)
        }
    }
    
}

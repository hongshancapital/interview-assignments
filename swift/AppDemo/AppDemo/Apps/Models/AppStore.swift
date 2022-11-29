//
//  AppStore.swift
//  AppDemo
//
//  Created by jaly on 2022/11/29.
//

import Foundation

struct AppState: MainState {
    var apps: [AppModel]? = nil
    var favList: Set<String> = []
    var hasMore: Bool = true
    var maxCount: Int = 30 // for test
    var offset: Int = 0
    var entity: String = "software"
    var limit: Int = 10
    var term: String = "chat"
    
}

enum AppAction: MainAction {
    case refresh
    case loadMore
    case toggleFav(String)
}

struct AppReduce: MainReduce {
    static let searchUrl = "https://itunes.apple.com/search"
    
    func reduce(state: AppState, action: AppAction) async -> AppState {
        switch action {
        case .refresh:
            return await refresh(state: state)
        case .loadMore:
            return await loadMore(state: state)
        case .toggleFav(let bundleId):
            var newState = state
            if newState.favList.contains(bundleId) {
                newState.favList.remove(bundleId)
            } else {
                newState.favList.update(with: bundleId)
            }
            return newState
        }
    }
    
    func loadMore(state: AppState) async -> AppState {
        var newState = state
        let total = newState.apps?.count ?? 0
        newState.offset = newState.offset + total
        if let apps = await request(state: newState) {
            newState.apps?.append(contentsOf: apps)
            // Just For Test
            if total + apps.count >= newState.maxCount {
                newState.hasMore = false
            }
        }
        return newState
    }
    
    func refresh(state: AppState) async -> AppState {
        var newState = state
        newState.offset = 0
        newState.hasMore = true
        newState.apps = await request(state: newState)
        return newState
    }
    
    func request(state: AppState) async -> [AppModel]? {
        var querys: [String: Any] = [:]
        querys["entity"] = state.entity
        querys["limit"] = state.limit
        querys["term"] = state.term
        querys["offset"] = state.offset
        let queryString = querys.map {"\($0)=\($1)"}.joined(separator: "&")
        let url = "\(AppReduce.searchUrl)?\(queryString)"
        do {
            guard let requestUrl = URL(string: url) else {
                throw NSError(domain: "url init fatal", code: -1, userInfo: nil)
            }
            guard let (data, _) = try? await URLSession.shared.data(from: requestUrl) else {
                throw NSError(domain: "url request fatal", code: -1, userInfo: nil)
            }
            guard let json = try? JSONDecoder().decode(ListModel<AppModel>.self, from: data) else {
                throw NSError(domain: "json decoder fatal", code: -1, userInfo: nil)
            }
            return json.results
        }catch {
            print("error: \(error)")
            return []
        }
    }
}

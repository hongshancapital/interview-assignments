//
//  AppStore.swift
//  AppList
//
//  Created by haojiajia on 2022/7/10.
//

import Foundation
import Combine

class AppStore: ObservableObject {
    
    @Published var appState = AppState()
    
    func dispatch(_ action: AppAction) {
        Log("[ACTION]:\(action)")
        let result = AppStore.generateNewAction(action: action, on: appState)
        appState = result.0
        if let command = result.1 {
            Log("[COMMAND]: \(command)")
            command.execute(in: self)
        }
    }
    
    static func generateNewAction(action: AppAction, on state: AppState) -> (AppState, AppComand?) {
        var appState = state
        var appCommand: AppComand?
        
        switch action {
        case .loadApps:
            if appState.isRefreshing {
                break
            }
            appState.appsLoadingError = nil
            appState.isRefreshing = true
            appState.footerState = .idle
            appCommand = AppListCommand()
        case .loadAppsDone(let result):
            appState.appsLoadingError = nil
            appState.isRefreshing = false
            appState.footerState = .idle
            switch result {
            case .success(let list):
                appState.apps = list
            case .failure(let error):
                appState.appsLoadingError = error
            }
        case .loadMoreApps:
            if appState.footerState == .refreshing ||
                appState.footerState == .noMoreData ||
                appState.isRefreshing {
                break
            }
            appState.appsLoadingError = nil
            appState.footerState = .refreshing
            appCommand = AppListMoreCommand()
        case .loadMoreAppsDone(result: let result, isEnd: let isEnd):
            appState.footerState = isEnd ? .noMoreData : .idle
            switch result {
            case .success(let list):
                var newList = appState.apps
                newList += list
                appState.apps = newList.removeDuplicate()
            case .failure(let error):
                appState.appsLoadingError = error
                appState.footerState = .refreshFailed
            }
        case .toggleLike(let id):
            guard appState.apps.count > 0 else {
                break
            }
            if let index = appState.apps.firstIndex(where: { element in
                return element.id == id
            }) {
                appState.apps[index].isLike.toggle()
            } else {
                fatalError("not found index model id: \(id)")
            }
        }
        return (appState, appCommand)
    }
}

extension AppStore {
    static var sample: AppStore {
        let s = AppStore()
        s.appState.apps = AppStore.sampleAll()
        return s
    }
    
    static func sample(index: Int) -> AppItem {
        guard index < sampleAll().count else {
            fatalError("index out of range")
        }
        return sampleAll()[index]
    }
    
    static func sampleAll() -> [AppItem] {
        return FileHelper.loadJSON(from: "mock", as: AppContent.self).results
    }
    
}

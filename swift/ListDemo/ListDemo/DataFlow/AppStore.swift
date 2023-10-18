//
//  AppStore.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Combine
import Foundation
class AppStore: ObservableObject {

    @Published var appState = AppState()
    var disposeBag = DisposeBag()

    func dispatch(_ action: AppAction) {
        debugPrint("🦁️action: \(action)")
        let result = AppStore.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            debugPrint("🦁️command: \(command)")
            command.execute(in: self)
        }
    }

    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand? = nil
        switch action {
        case .refresh, .loadMore:
            if appState.appList.isLoading {
                break
            }
            
            appState.appList.appsLoadingError = nil
            appState.appList.isLoading = true
            if case .refresh = action {
                appState.appList.hasMore = true
                appCommand = RefreshAppListCommand()
            } else {
                appCommand = LoadMoreAppListCommand()
            }
            

        case .refreshDone(let result), .loadMoreDone(let result):
            appState.appList.isLoading = false

            switch result {
            case .success(let models):
                handleFavoriate(appState: &appState, models: models)
                if case .refreshDone(_) = action {
                    appState.appList.apps = nil
                    appState.favoriteappIDs.removeAll()
                    appState.appList.apps = models
                } else {
                    var oldApps = appState.appList.apps
                    let newApps: [AppListViewModel]? = models
                    debugPrint("🦁️oldApps: \(String(describing: oldApps?.count)) - newApps: \(String(describing: newApps?.count))")
                    if let newApps = newApps {
                        appState.appList.hasMore = !(newApps.count < appState.appList.pageAppsNum)
                        oldApps? += newApps
                        debugPrint("🦁️totalApps: \(String(describing: oldApps?.count))")
                    } else {
                        appState.appList.hasMore = false
                    }
                    
                    appState.appList.apps = oldApps
                }
                
                appState.appList.pageIndex += 1
                
            case .failure(let error):
                appState.appList.appsLoadingError = error
            }
        case .toggleFavorite(let id):
            var newFavorites = appState.favoriteappIDs
            if newFavorites.contains(id) {
                newFavorites.remove(id)
            } else {
                newFavorites.insert(id)
            }
            appState.favoriteappIDs = newFavorites
            
        case .clearCache:
            appState.appList.apps = nil
            appState.favoriteappIDs.removeAll()
            appCommand = ClearCacheCommand()
        }
        return (appState, appCommand)
    }
    
    private static func handleFavoriate(appState: inout AppState, models: [AppListViewModel]) {
        // 处理isFavorite 状态
        var newFavorites = appState.favoriteappIDs
        for model in models {
            if model.isFavorite && !appState.isFavoriteApp(id: model.id) {
                newFavorites.insert(model.id)
            }
            
            if !model.isFavorite && appState.isFavoriteApp(id: model.id) {
                newFavorites.remove(model.id)
            }
        }
        appState.favoriteappIDs = newFavorites
    }
}

class DisposeBag {
    private var values: [AnyCancellable] = []
    func add(_ value: AnyCancellable) {
        values.append(value)
    }
}

extension AnyCancellable {
    func add(to bag: DisposeBag) {
        bag.add(self)
    }
}

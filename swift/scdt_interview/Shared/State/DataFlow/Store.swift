//
//  Store.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Combine

class Store: ObservableObject {
    @Published var appState = AppState()

    private var disposeBag = Set<AnyCancellable>()

    init() {
        setupObservers()
    }

    func setupObservers() {}

    func dispatch(_ action: AppAction) {
//        print("[ACTION]: \(action)")
        let result = Store.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            print("[COMMAND]: \(command)")
            command.execute(in: self)
        }
    }

    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand?

        switch action {
        case .loadAppList:
            if appState.appList.loadingApps {
                break
            }
            appState.appList.loadingApps = true
            appState.appList.appsLoadingError = nil
            appCommand = LoadAppListCommand(pageSize: 10, pageIndex: appState.appList.pageIndex, type: .loadData)

        case .loadAppListDone(result: let result):
            appState.appList.loadingApps = false
            switch result {
            case .success(let models):
                appState.appList.pageIndex = 0
                appState.appList.list = models
            case .failure(let error):
                appState.appList.appsLoadingError = error
            }

        case .loadAppListHeader:
            appState.appList.headerRefresh = true
            appCommand = LoadAppListCommand(pageSize: 10, pageIndex: appState.appList.pageIndex, type: .headerRefresh)

        case .loadAppListHeaderDone(result: let result):
            appState.appList.headerRefresh = false
            switch result {
            case .success(let models):
                appState.appList.pageIndex = 0
                appState.appList.list = models
            case .failure(let error):
                appState.appList.appsLoadingError = error
            }

        case .loadAppListFooter(index: let index):
            appCommand = LoadAppListCommand(pageSize: appState.appList.pageSize, pageIndex: appState.appList.pageIndex, type: .footerRefresh)

        case .loadAppListFooterDone(result: let result):
            switch result {
            case .success(let models):
                if !models.isEmpty {
                    appState.appList.pageIndex += 1
                }
                appState.appList.hasMoreData = models.count >= appState.appList.pageSize
                appState.appList.list?.append(contentsOf: models)
            case .failure(let error):
                appState.appList.appsLoadingError = error
            }
            
        case .toggleFavorite(let id):
            let user = appState.user
            
            var newFavorites = user.favoriteAppIDs
            if newFavorites.contains(id) {
                newFavorites.remove(id)
            } else {
                newFavorites.insert(id)
            }
            appState.user.favoriteAppIDs = newFavorites

        }

        return (appState, appCommand)
    }
}

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
            appCommand = LoadAppListCommand(pageSize: appState.appList.pageSize, pageIndex: 0, type: .loadData)

        case .loadAppListDone(result: let result):
            appState.appList.loadingApps = false
            switch result {
            case .success(let models):
                appState.appList.pageIndex = 0
                appState.appList.list = models
                appState.appList.listState.setNoMore(false)

            case .failure(let error):
                appState.appList.appsLoadingError = error
            }

        case .loadAppListHeader:
            appState.appList.headerRefresh = true
            appState.appList.footerRefresh = false
            appCommand = LoadAppListCommand(pageSize: appState.appList.pageSize, pageIndex: 0, type: .headerRefresh)

        case .loadAppListHeaderDone(result: let result):
            appState.appList.headerRefresh = false
            switch result {
            case .success(let models):
                appState.appList.pageIndex = 0
                appState.appList.list = models
                appState.appList.listState.setNoMore(false)

            case .failure(let error):
                appState.appList.appsLoadingError = error
            }

        case .loadAppListFooter(index: let index):
            appState.appList.headerRefresh = false
            appState.appList.footerRefresh = true
            appCommand = LoadAppListCommand(pageSize: appState.appList.pageSize, pageIndex: index, type: .footerRefresh)

        case .loadAppListFooterDone(result: let result):
            switch result {
            case .success(let models):
                if !models.isEmpty {
                    appState.appList.pageIndex += 1
                }
                appState.appList.listState.setNoMore(models.count < appState.appList.pageSize)
                appState.appList.list?.append(contentsOf: models)
            case .failure(let error):
                appState.appList.appsLoadingError = error
            }
            appState.appList.footerRefresh = false
            
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

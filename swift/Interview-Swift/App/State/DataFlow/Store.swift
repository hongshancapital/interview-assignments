//
//  Store.swift
//  App
//
//  Created by lizhao on 2022/9/24.
//

import Combine
import GCore
class Store: ObservableObject {
    @Published var appState = AppState()
    
    private var disposeBag = Set<AnyCancellable>()
 
    let networkProvider = NetworkProvider.build(AppRequestsTarget.self)
    
    func dispatch(_ action: AppAction) {
        ConsoleLog.debug("[ACTION]: \(action)")
        let (state, cmd) = Store.reduce(state: appState, action: action)
        self.appState = state
        if let cmd = cmd {
            ConsoleLog.debug("[Command]: \(action)")
            cmd.execute(in: self)
        }
    }
    
    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand? = nil
        
        switch action {
        case .refresh:
            if appState.appList.loadingList {
                break
            }
            appState.appList.loadingListError = nil
            appState.appList.loadingList = true
            appState.appList.canLoadMore = true
            appState.appList.loadmoreState = .hidden
            appState.appList.page = 1
            appCommand = LoadAppListCommand(page: 1)
            
        case .loadApplist:
            if appState.appList.loadingList {
                break
            }
            if appState.appList.canLoadMore == false {
                break
            }
            let page = appState.appList.page
            appState.appList.loadingListError = nil
            appState.appList.loadingList = true
            appState.appList.canLoadMore = true
            appCommand = LoadAppListCommand(page: page)
            
        case .loadApplistDone(let result):
            appState.appList.loadingList = false
            switch result {
            case .success(let models):
                if models.isEmpty {
                    appState.appList.canLoadMore = false
                    appState.appList.loadmoreState = .noMoreData
                    break
                }
                let page = appState.appList.page
                if page == 1 {
                    appState.appList.apps = models
                } else {
                    appState.appList.apps += models
                }
                appState.appList.loadmoreState = .loading
                appState.appList.page += 1
            case .failure(let error):
                appState.appList.loadingListError = error
            }
        case .toggleFavorite(let trackId, let isFavorite):
            appCommand = FavoriteAppCommand(id: trackId, isFavorite: isFavorite)
        
        case .toggleFavoriteDone(let result):
            switch result {
            case .success(let res):
                appState.appList.updateFavoriteState(id: res.id, isFavorite: res.isFavorited)
            case .failure(let e):
                ConsoleLog.error(e.localizedDescription)
                break
            }
        }
        
        return (appState, appCommand)
    }
}

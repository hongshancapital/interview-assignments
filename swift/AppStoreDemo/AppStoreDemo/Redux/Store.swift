//
//  Store.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import Foundation
import Combine


enum AppAction {
    
    /// The done callback is to adapt the ScrollView's redreshable async function, though it does not match Redux's design principles.
    case refresh(_ done: (() -> Void)? = nil)
    case loadMore
    case endRefresh(result: Result<[AppItem], AppError>)
    case endLoadMore(result: Result<[AppItem], AppError>)
    case endProcessData(data: [AppItem], isEnd: Bool)
    case clickFavor(favor: Bool, id: Int64)
}

/// State of Main View
enum MainViewStatus {
    case firstLoading
    case refreshing
    case normal
    case loadingMore
    case noMoreData
    case error(AppError)
}

struct AppState {
    
    var data: [AppItem] = []
    var isRefreshing: Bool = false
    var isLoadingMore: Bool = false
    var isEnd: Bool = false
    var error: AppError?
    var collectedIDs: Set<Int64> = Set()
    
    var status: MainViewStatus {
        get {
            if let error = error {
                return .error(error)
            }
            switch (data.isEmpty, isRefreshing, isLoadingMore, isEnd) {
            case (true, _, _, _):
                return .firstLoading
            case (false, true, _, _):
                return .refreshing
            case (false, _, true, _):
                return .loadingMore
            case (false, _, _, true):
                return .noMoreData
            default:
                return .normal
            }
        } set {}
    }
}


/// 全局数据源
final class Store: ObservableObject {
    
    @Published var state: AppState = AppState()
    
    private lazy var mainStatusPublisher = PassthroughSubject<MainViewStatus, Never>()
    private var disposeBag = Set<AnyCancellable>()
    
    func dispatch(_ action: AppAction) {
        
        #if DEBUG
        print("Action: \("\(action)".prefix(1000))")
        #endif
        
        let result = Self.reduce(state: state, action: action)
        state = result.state
        
        if let command = result.command {
            command.execute(in: self)
        }
        
        mainStatusPublisher.send(state.status)
    }
     
    func asyncDispatch(_ action: AppAction) async {
        guard case .refresh = action else {
            dispatch(action)
            return
        }
        await withUnsafeContinuation({ continuation in
            DispatchQueue.main.async {
                self.dispatch(.refresh({ continuation.resume() }))
            }
        })
    }
    
    static func reduce(state: AppState, action: AppAction) -> (state: AppState, command: AppCommand?) {
        
        var appState = state
        var command: AppCommand?
        
        switch action {
        case .refresh(let finish):
            guard !appState.isRefreshing else {
                break
            }
            appState.isRefreshing = true
            appState.isEnd = false
            appState.error = nil
            command = RequestCommend(isRefresh: true, finish: finish)
        case .loadMore:
            guard !appState.isLoadingMore else {
                break
            }
            appState.isLoadingMore = true
            appState.error = nil
            command = RequestCommend(isRefresh: false)

        case .endRefresh(let result):
            switch result {
            case .success(let data):
                command = DataPreprocessCommand(input: data, state: appState, isRefresh: true)
            case .failure(let error):
                appState.error = error
                appState.isRefreshing = false
                break
            }
        case .endLoadMore(let result):
            switch result {
            case .success(let data):
                command = DataPreprocessCommand(input: data, state: appState, isRefresh: false)
            case .failure(let error):
                appState.error = error
                appState.isLoadingMore = false
            }
        case .endProcessData(let data, let isEnd):
            appState.data = data
            appState.isEnd = isEnd
            appState.isRefreshing = false
            appState.isLoadingMore = false
        case .clickFavor(let favor, let id):
            var collectedIds = appState.collectedIDs
            if favor {
                collectedIds.insert(id)
            } else {
                collectedIds.remove(id)
            }
            appState.collectedIDs = collectedIds
            guard let index = appState.data.firstIndex(where: { $0.id == id }) else { break }
            var item = appState.data[index]
            item.collected = favor
            appState.data[index] = item
        }
        return (appState, command)
    }
    
}

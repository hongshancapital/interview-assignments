//
//  File.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/1.
//

import Foundation
import Combine

///loading状态
enum AppDataLoadingState {
    case initial
    case refreshing
    case loadingNextPage
    case endOfData
    case loadFinish
}

///App当前展示的数据
struct AppState {
    ///展示的数据
    var appIdSet: Set<String> = Set<String>()
    var appInfoModels: [AppInfoViewModel] = []
    var appDataLoadingState: AppDataLoadingState = .initial
    
    ///错误提示
    var displayAlert: Bool = false
    var error: Error? = nil
    
}

///存储App状态
class Store: ObservableObject {
    @Published var appState = AppState()
    
    var disposeBag = Set<AnyCancellable>()
        
    /// appState转换方法
    /// - Parameters:
    ///   - state: 当前的appState
    ///   - action: 当前动作
    /// - Returns: - state: 新的appState; - appCommand:
    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var curState = state
        var command: AppCommand? = nil
        curState.error = nil
        curState.displayAlert = false
        switch action {
            ///首页数据加载
        case .refreshData:
            command = AppRefreshData()
            curState.appDataLoadingState = .refreshing
            
            
            ///首页数据加载完毕
        case .refreshDataDone(let result):
            switch result {
            case .success(let viewModelList):
                curState.appIdSet.removeAll()
                viewModelList.map({$0.appId}).forEach { theAppid in
                    curState.appIdSet.insert(theAppid)
                }
                curState.appInfoModels = viewModelList
                
            case .failure(let error):
                curState.error = error
                curState.displayAlert = true
                break
            }
            curState.appDataLoadingState = .loadFinish
            
            
            ///加载下一页数据
        case .loadNextPage:
            command = AppLoadMoreData(curDataCount: curState.appInfoModels.count)
            curState.appDataLoadingState = .loadingNextPage
            ///加载下一页数据完毕
        case .loadNextPageDone(result: let result):
            switch result {
            case .success(let viewModelList):
                viewModelList.forEach { viewModel in
                    if !curState.appIdSet.contains(viewModel.appId) {
                        curState.appInfoModels.append(viewModel)
                        curState.appIdSet.insert(viewModel.appId)
                    }
                }
                
            case .failure(let error):
                curState.error = error
                curState.displayAlert = true
                break
            }
            curState.appDataLoadingState = .loadFinish
            
            ///点击喜欢
        case .tapLike(appId: let appId):
            if let appViewModel = curState.appInfoModels.first(where: { $0.appId == appId }) {
                appViewModel.toggleLikeState()
            }
            
            ///没有后续数据
        case .endOfData:
            curState.appDataLoadingState = .endOfData
        }
        return (curState, command)
    }
    
    
    func dispatch(_ action: AppAction) {
        print("[ACTION]: \(action)")
        let result = Store.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            print("[COMMAND]: \(command)")
            command.execute(in: self)
        }
    }
}


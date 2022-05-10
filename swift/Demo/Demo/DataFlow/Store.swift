//
//  Store.swift
//  Demo
//
//  Created by Kai on 2022/2/22.
//

import Foundation
import Combine
import CloudKit

class Store: ObservableObject {
    @Published var appState = AppState()
    let bag = DisposeBag()
    func dispatch(_ action: AppAction) {
        let result = Store.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            command.execute(store: self)
        }
    }
    
    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand?
        
        switch action {
        case .loadAllList:
            appCommand = LoadAllListCommon()
            break
            
        case .loadPageList(page: let page):
            appCommand = LoadPageListCommon(page: page)
            
        case .changeLikeState(model: let model):
            appCommand = ChangeModelLikeCommand(model: model)
            
        case .loadAllListDone(result: let result):
            switch result {
            case .success(let data):
                appState.appAllList = data.results
                appState.appPageList = Array(data.results[0..<20])
            case .failure(let error):
                print(error)
            }
            
        case .loadPageListDone(result: let result):
            switch result {
            case .success(let list):
                appState.appPageList = list
            case .failure(let error):
                print(error)
            }
            
        case .changeLikeStateDone(result: let result):
            break
        }
        
        return (appState, appCommand)
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

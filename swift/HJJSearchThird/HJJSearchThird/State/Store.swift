//
//  Store.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import Foundation
import Combine

class Store: ObservableObject {
  
    @Published var appState = AppState()
    var disposeBag = [AnyCancellable]()

    func executeAction(action: AppAction) {
        #if DEBUG
        print("[ACTION]: \(action)")
        #endif
        
        switch action {
        case .startServer(result: let result):
            switch result {
            case .success(_):
                print("服务器连接成功")
            case .failure(let error):
                appState.loadGoodsError = error
            }
            
        case .loadGoods:
            guard !appState.isLoadingGoods else {
                break
            }
            appState.isLoadingGoods = true
            GoodsViewModel().loadGoods(in: self)
            
        case .loadGoodsFinished(result: let result):
            appState.isLoadingGoods = false
            switch result {
            case .success(let goods):
                appState.filterGoods = goods
                appState.goodsChecker.goods = goods
            case .failure(let error):
                appState.loadGoodsError = error
            }
            
        case .filterGoods(goods: let goods):
            appState.filterGoods = goods
        }
        
    }
    
    init() {
        subscribeAppStatePublisher()
    }
    
    func subscribeAppStatePublisher() {
        appState.goodsChecker.filteredPublisher.sink { (goods) in
            self.executeAction(action: .filterGoods(goods: goods))
        }.store(in: &disposeBag)
    }

}


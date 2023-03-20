//
//  AppCommand.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import Foundation
import Combine

protocol AppCommand {
    func execute(in store: Store)
}

struct RequestCommend: AppCommand {
    
    let isRefresh: Bool
    var finish: (() -> Void)?
    
    func execute(in store: Store) {
        let token = SubscriptionToken()
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat") else {
            return
        }
        AppRequest().publisher(url: url)
            .sink { completion in
                switch completion {
                case .finished:
                    break
                case .failure(let error):
                    if isRefresh {
                        store.dispatch(.endRefresh(result: .failure(error)))
                    } else {
                        store.dispatch(.endLoadMore(result: .failure(error)))
                    }
                }
                finish?()
                token.unseal()
            } receiveValue: { data in
                if isRefresh {
                    store.dispatch(.endRefresh(result: .success(data)))
                } else {
                    store.dispatch(.endLoadMore(result: .success(data)))
                }
            }
            .seal(in: token)
    }
}

struct DataPreprocessCommand: AppCommand {
    
    let input: [AppItem]
    let state: AppState
    let isRefresh: Bool
    
    func execute(in store: Store) {
        let data = input.map({ item in
            var i = item
            i.collected = state.collectedIDs.contains(i.id)
            return i
        })
        if isRefresh {
            var result: [AppItem]
            var isEnd = state.isEnd
            if data.count > 10 {
                result = Array(data.prefix(10))
            } else {
                isEnd = true
                result = data
            }
            store.dispatch(.endProcessData(data: result, isEnd: isEnd))
        } else {
            let startIndex = state.data.count
            let endIndex = startIndex + 10
            var result: [AppItem] = []
            var isEnd = state.isEnd
            if startIndex > data.count - 1 {
                isEnd = true
            } else if endIndex > data.count - 1 {
                result = state.data + Array(data[startIndex..<data.count-1])
                isEnd = true
            } else {
                result = state.data + Array(data[startIndex..<endIndex])
            }
            store.dispatch(.endProcessData(data: result, isEnd: isEnd))
        }
        
    }
    
}

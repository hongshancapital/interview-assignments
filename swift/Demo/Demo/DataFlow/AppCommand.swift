//
//  AppCommand.swift
//  Demo
//
//  Created by Kai on 2022/2/23.
//

import Foundation
import Combine

protocol AppCommand {
    func execute(store: Store)
}


struct LoadAllListCommon: AppCommand {
    
    func execute(store: Store) {
        
        store.appState.isNoMoreData = false
        LoadAllListRequest().publisher()
            .sink(receiveCompletion: { complete in
                if case .failure(let error) = complete {
                    store.dispatch(.loadAllListDone(result: .failure(error)))
                }
            }, receiveValue: { data in
                store.dispatch(.loadAllListDone(result: .success(data)))
            }).add(to: store.bag)
    }
}

struct LoadPageListCommon: AppCommand {
    let page: Int
    
    func execute(store: Store) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
            let pageCount = 20
            var count = page * pageCount + pageCount
            if count > store.appState.appAllList.count {
                count = store.appState.appAllList.count
                store.appState.isNoMoreData = true
            } else {
                store.appState.isNoMoreData = false
            }
            let list = Array(store.appState.appAllList[0..<count])
            store.dispatch(.loadPageListDone(result: .success(list)))
        }
    }
}


struct ChangeModelLikeCommand: AppCommand {
    let model: KKModel
    
    func execute(store: Store) {
        
        if let index = store.appState.appPageList.firstIndex(where: { $0.id == model.id }) {
            let item = store.appState.appPageList[index]
            if item.like == true {
                store.appState.appPageList[index].like = false
            } else {
                store.appState.appPageList[index].like = true
            }
        }
        
        
        if let index = store.appState.appAllList.firstIndex(where: { $0.id == model.id }) {
            let item = store.appState.appAllList[index]
            if item.like == true {
                store.appState.appAllList[index].like = false
            } else {
                store.appState.appAllList[index].like = true
            }
        }
        store.dispatch(.changeLikeStateDone(result: model.id))
    }
}

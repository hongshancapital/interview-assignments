//
//  ListViewModel.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya
import Combine
import SwiftUI

class ListViewModel: ViewModel, ServiceContainer {
    
    enum Action {
        case refresh(isFirst: Bool)
    }
    
    // state
    @Published var list: [SocialApp] = []
    @Published var refreshing: Bool = false
    @Published var hasMore: Bool = false
    @Published var showEmptyView: Bool = false
    
    private var page = RefreshPage(value: 1)
    private let pageSize = 20
    var database: [String: Bool] {
        set {
            UserDefaults.set(newValue, forKey: "database")
        }
        get {
            if let value = UserDefaults.value(key: "database") as? [String: Bool] {
                return value
            } else {
                let value = [String: Bool]()
                UserDefaults.set(value, forKey: "database")
                return value
            }
        }
    }
        
    func mutate(action: Action) {
        switch action {
        case let .refresh(isFirst):
            let index = isFirst ? page.firstValue : page.value + 1
            indonesService
                .appList(pageSize: pageSize, index: index)
                .sink(onValue: { [weak self] resp in
                    guard let self = self else { return }
                    
                    var newList: [SocialApp] = []
                    for var model in resp.results {
                        model.isLiked = self.database[model.id] ?? false
                        self.database[model.id] = model.isLiked
                        newList.append(model)
                    }
                    
                    self.refreshing = false
                    self.hasMore = newList.count >= self.pageSize
                    
                    if isFirst {
                        self.page.value = index
                        self.list = newList
                        self.showEmptyView = self.list.isEmpty
                        return
                    }
                    
                    if self.page.isValidMoreData(page: index) {
                        if !newList.isEmpty {
                            self.page.value = index
                        }
                        self.list = self.list + newList
                    }
                    
                }, onError: { [weak self] error in
                    guard let self = self else { return }
                    self.refreshing = false
                    self.hasMore = true
                    self.showEmptyView = self.list.isEmpty
                })
                .store(in: &storage)
        }
    }
}

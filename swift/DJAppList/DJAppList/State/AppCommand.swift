//
//  AppCommand.swift
//  AppList
//
//  Created by haojiajia on 2022/7/10.
//

import Foundation
import Combine

protocol AppComand {
    func execute(in store: AppStore)
}

struct AppListCommand: AppComand {
    
    func execute(in store: AppStore) {
        let token = SubscriptionToken()
        // 模拟弱网情况
        DispatchQueue.global().asyncAfter(deadline: .now() + 1.5) {
            AppListRequest.publisher(0)
                .sink { complete in
                    if case .failure(let error) = complete {
                        store.dispatch(.loadAppsDone(result: .failure(error)))
                        token.unseal()
                    }
                } receiveValue: { value in
                    store.dispatch(.loadAppsDone(result: .success(value)))
                }
                .seal(in: token)
        }
    }
}

struct AppListMoreCommand: AppComand {
    
    func execute(in store: AppStore) {
        let token = SubscriptionToken()
        // 模拟弱网情况
        DispatchQueue.global().asyncAfter(deadline: .now() + 1.5) {
            AppListRequest.publisher(store.appState.apps.count)
                .sink { complete in
                    if case .failure(let error) = complete {
                        store.dispatch(.loadMoreAppsDone(result: .failure(error), isEnd: false))
                        token.unseal()
                    }
                } receiveValue: { value in
                    // 模拟无更多数据
                    if store.appState.apps.count >= 20 {
                        store.dispatch(.loadMoreAppsDone(result: .success(value), isEnd: true))
                    } else {
                        store.dispatch(.loadMoreAppsDone(result: .success(value), isEnd: false))
                    }
                }
                .seal(in: token)
        }
    }
    
}

class SubscriptionToken {
    var cancellable: AnyCancellable?
    func unseal() { cancellable = nil }
}

extension AnyCancellable {
    func seal(in token: SubscriptionToken) {
        token.cancellable = self
    }
}


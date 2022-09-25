//
//  AppCommand.swift
//  App
//
//  Created by lizhao on 2022/9/24.
//

import Foundation
import Combine
import Moya

protocol AppCommand {
    func execute(in store: Store)
}

struct LoadAppListCommand: AppCommand {
    let page: Int
    
    func execute(in store: Store) {
        let token = SubscriptionToken()
 
        AppListRequest(page: page)
            .sendRequest(dependence: store.networkProvider)
            .sink { complete in
                if case .failure(let e) = complete {
                    store.dispatch(.loadApplistDone(result: .failure(e)))
                }
                token.unseal()
            } receiveValue: { value in
                store.dispatch(.loadApplistDone(result: .success(value)))
            }
            .seal(in: token)
    }
}

struct FavoriteAppCommand: AppCommand {
    let id: Int
    let isFavorite: Bool
    let networkProvider = MoyaProvider<AppRequestsTarget>(stubClosure: MoyaProvider.delayedStub(0.2))
    
    func execute(in store: Store) {
        let token = SubscriptionToken()
        FavoriteAppRequest(id: id, isFavorited: isFavorite)
            .sendRequest(dependence: networkProvider)
            .sink { complete in
                if case .failure(let e) = complete {
                    store.dispatch(.toggleFavoriteDone(result: .failure(e)))
                }
                token.unseal()
            } receiveValue: { value in
                store.dispatch(.toggleFavoriteDone(result: .success(value)))
            }
            .seal(in: token)
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

//
//  GoodsViewModel.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import Foundation
import Combine

struct GoodsViewModel {
    
    func loadGoods(in store: Store)  {
        let httpServer = LocalHTTPServer.shared
        if !httpServer.isOPen {
            guard httpServer.startServer() else {
                store.executeAction(action: .startServer(result: .failure(.serverConnectFail)))
                return
            }
        }
                
        let token = SubscriptionToken()
        LoadGoodsRequest(goodsURL: LocalHTTPServer.shared.goodsURL!)
            .publisher
            .sink { (complete) in
                if case .failure(let error) = complete {
                    print(error.localizedDescription)
                    store.executeAction(action: .loadGoodsFinished(result: .failure(error)))
                }
                token.unseal()

            } receiveValue: { (goods) in
                //print("goods: \(goods)")
                store.executeAction(action: .loadGoodsFinished(result: .success(goods)))

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

//
//  SubscriptionToken.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Combine
class SubscriptionToken {
    var cancellable: AnyCancellable?
    func unseal() { cancellable = nil }
}

extension AnyCancellable {
    func seal(in token: SubscriptionToken) {
        token.cancellable = self
    }
}

//
//  SubscriptionToken.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import Foundation
import Combine

final class SubscriptionToken {
    
    var canellable: AnyCancellable?
    
    func unseal() {
        canellable = nil
    }
}

extension AnyCancellable {
    
    func seal(in token: SubscriptionToken) {
        token.canellable = self
    }
}

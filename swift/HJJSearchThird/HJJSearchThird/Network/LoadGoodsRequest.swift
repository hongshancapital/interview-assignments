//
//  LoadGoodsRequest.swift
//  HJJSearchUpgrade
//
//  Created by haojiajia on 2021/1/4.
//

import Foundation
import Combine

struct LoadGoodsRequest {
    var goodsURL: URL
    
    var publisher: AnyPublisher<[GoodsModel], AppError> {
        URLSession.shared
            .dataTaskPublisher(for: self.goodsURL)
            .delay(for: 2, scheduler: DispatchQueue.main)
            .map { $0.data }
            .decode(type: [GoodsModel].self, decoder: appDecoder)
            .mapError { AppError.networkingFailed($0) }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}





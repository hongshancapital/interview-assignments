//
//  MockModel.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import Foundation

// An convenient model for decoding data from `mock.json`.
struct MockModel: Decodable {
    let totalNumber: Int
    let products: [Product]

    enum CodingKeys: String, CodingKey {
        case totalNumber = "resultCount"
        case products = "results"
    }
}

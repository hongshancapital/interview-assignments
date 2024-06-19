//
//  ProductProviderResponse.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import Foundation

// An convenient model for response of `ProductPrivider.product(forPage:opacity:)` API
struct ProductProviderResponse: Equatable {
    let totalNumber: Int
    let products: [Product]
}

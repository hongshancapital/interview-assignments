//
//  Model.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import Foundation

enum ProductState {
    case inStock, outOfStock
}

struct Vendor: Codable, Identifiable, Hashable {
    var id: Int = 0
    var name: String = ""
    var kinds: [Kind] = []
}

struct Kind: Codable, Identifiable, Hashable {
    var id: Int = 0
    var name: String = ""
    var products: [Product] = []
}

struct Product: Codable, Identifiable, Hashable {
    var id: Int = 0
    var name: String = ""
    var price: Double = 0
    var inStock: Bool = false
}

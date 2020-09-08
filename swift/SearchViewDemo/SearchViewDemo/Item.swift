//
//  Item.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import Foundation

struct Item: Decodable, Identifiable {
    var id: Int
    var name: String
    var price: Decimal
    var status: ItemStatus

    static var defaultValue: Item {
        Item(id: 1, name: "V11", price: 0, status: .inStock)
    }
}

enum ItemStatus: Int, Decodable {
    case inStock
    case outStock

    var displayText: String {
        switch self {
        case .inStock: return NSLocalizedString("In-stock", comment: "")
        case .outStock: return NSLocalizedString("Out-of-stock", comment: "")
        }
    }
}

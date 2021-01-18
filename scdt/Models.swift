//
//  Models.swift
//  scdt
//
//  Created by qiupeng on 2021/1/18.
//

import Foundation

struct SearchListSectionModel: Identifiable, Codable {
    var id: String
    var sectionName: String

    struct ItemModel: Identifiable, Codable {
        var id: String
        var name: String
        var inStock: Bool
        var price: String
    }
    var list: [ItemModel]
}

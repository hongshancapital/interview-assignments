//
//  GoodsModel.swift
//  HJJSearchUpgrade
//
//  Created by haojiajia on 2021/1/3.
//

import Foundation
import Combine

struct GoodsModel: Identifiable, Codable {
    var id: Int
    var category: String
    var brand: String
    var items: [GoodsDescModel]
}

struct GoodsDescModel: Identifiable, Codable {
    var id : Int
    var name: String
    var isInStock: Bool
    var price: Double
}





//
//  SearchResultModel.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

struct SearchResultModel: Hashable, Codable, Identifiable {
    var id: Int             = 0
    var name: String        = ""
    var category: String    = ""
    var price: Double       = 0.0
    var isInStock: Bool     = false
    var isAvailable: Bool   = false
    var isInStockString: String {
        get {
            return isInStock ? "In-stock" : "Out-of-stock"
        }
    }
    
}

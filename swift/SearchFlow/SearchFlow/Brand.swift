//
//  Brand.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import Foundation

struct Brands : Codable {
    var brands: [Brand]
}

struct Brand : Codable, Identifiable {
    var id: Int
    var brand: String
    var series: [Serie]
}

struct Serie : Codable, Identifiable {
    var id: Int
    var name: String
    var elements: [Element]
}

struct Element : Codable, Identifiable {
    var id: Int
    var name: String
    var price: Decimal
    var status: Status
    
    // MARK: Nested Type
    enum Status : Int, Codable {
        case inStock, outStock
        
        var title: String {
            switch self {
            case .inStock: return "In-stock"
            case .outStock: return "Out-of-stock"
            }
        }
    }
}

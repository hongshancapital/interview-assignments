//
//  SearchModel.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

struct SearchModel: Codable, Identifiable {
    
    /// 利用UUID作为唯一ID
    var id: UUID = UUID()
    
    /// 标题
    var title: String = ""
    
    /// item数据
    var items: [SearchItemModel] = []
    
}

struct SearchItemModel: Codable, Identifiable {
    
    /// Stock状态
    enum StockState: Int, Codable {
        case instock, outstock
        
        var value: String {
            switch self {
            case .instock: return "In-stock"
            case .outstock: return "Out-of-stock"
            }
        }
    }
    
    /// 唯一ID
    var id: UUID = UUID()
    
    /// 名称
    var name: String = ""
    
    /// 状态
    var stock: StockState = .instock
    
    /// 价格
    var price: Float = 0.0

    var priceText: String {
        return String(format: "$%.2f", price)
    }
    
}

extension SearchItemModel: Equatable {
    
    /// 用于判断
    static func == (lhs: Self, rhs: Self) -> Bool {
        return lhs.id == rhs.id
    }
    
}

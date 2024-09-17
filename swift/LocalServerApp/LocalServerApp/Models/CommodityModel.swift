//
//  SearchModel.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import Foundation

//MARK: - CommodityModel
struct CommodityModel: Codable, Identifiable {
    var id: UUID = UUID()
    let title: String
    let items: [CommodityItemModel]
}

//MARK: - CommodityItemModel
struct CommodityItemModel: Codable, Identifiable {
    enum StockStatus: Int, Codable {
        case instock, outstock
        
        var desc: String {
            switch self {
            case .instock: return "In-stock"
            case .outstock: return "Out-of-stock"
            }
        }
    }
    
    var id: UUID = UUID()
    var title: String
    var stockStatus: StockStatus
    var price: Double
    
    var priceText: String {
        String(format: "$%.2f", price)
    }
    
}

//MARK: - Equatable协议
extension CommodityItemModel: Equatable {
    static func == (lhs: Self, rhs: Self) -> Bool {
        lhs.id == rhs.id
    }
}

//MARK: - Mock
struct CommodityMock {
    static func mockPageOne() -> [CommodityModel] {
        // one
        let itemV11 = CommodityItemModel(title: "V11", stockStatus: .instock, price: 599.99)
        let itemV10 = CommodityItemModel(title: "V10", stockStatus: .outstock, price: 399.99)
        let model01 = CommodityModel(title: "Vacuum", items: [itemV11, itemV10])
        
        // two
        let itemSupersonic = CommodityItemModel(title: "Supersonic", stockStatus: .instock, price: 399.99)
        let model02 = CommodityModel(title: "Hair Dryer", items: [itemSupersonic])

        return [model01, model02]
    }
}

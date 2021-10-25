//
//  ProductModel.swift
//  HongShanApp
//
//  Created by wangbin on 2021/10/22.
//

import Foundation

struct ProductModel:  Identifiable{
    var id = UUID()
    
    var name: String
    var price: String
    var description: String
}

struct ProductHeaderModel:  Identifiable{
    var id = UUID()
    
    var title: String
    var products: [ProductModel]
}

var headerModels: [ProductHeaderModel] = [
    ProductHeaderModel(title: "Vacuum", products: [
        ProductModel(name: "V11", price: "599.99", description: "In-stock"),
        ProductModel(name: "V10", price: "399.99", description: "Out-of-stock"),
    ]),
    ProductHeaderModel(title: "Hair Dryer", products: [
        ProductModel(name: "Supersonic", price: "399.99", description: "In-stock"),
    ])
]

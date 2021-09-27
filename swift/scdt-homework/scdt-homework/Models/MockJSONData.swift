//
//  MockJSONData.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/27.
//

import Foundation

// MARK: - mock data
let mockJSONData: StockInfo = [
    StockInfoElement(id: 1, category: "Vacuum", content: [
        Content(name: "V09", type: "In-stock", price: 799.99, author: "Tom", frozen: 1),
        Content(name: "V10", type: "Out-stock", price: 399.99, author: "Dyson", frozen: 0),
        Content(name: "V11", type: "In-stock", price: 599.99, author: "Dyson", frozen: 1),
        Content(name: "V12", type: "Out-stock", price: 299.99, author: "Jack", frozen: 0)
    ]),
    StockInfoElement(id: 2, category: "Hair Dryer", content: [
        Content(name: "Supersonic", type: "In-stock", price: 599.99, author: "Dyson", frozen: 0),
        Content(name: "LiveForever", type: "Out-stock", price: 399.99, author: "Neil", frozen: 0),
        Content(name: "Stand by me", type: "In-stock", price: 299.99, author: "Neil", frozen: 1)
    ]),
]

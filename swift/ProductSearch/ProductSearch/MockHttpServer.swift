//
//  MockHttpServer.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import Foundation

class MockHttpServer: ObservableObject {
    static let shared = MockHttpServer()
    
    func request(searchText: String, pageIndex: Int) async throws -> (Data?, Bool) {
        print("load page \(pageIndex) started")
        try await Task.sleep(nanoseconds: UInt64.random(in: 500_000_000..<2_000_000_000))
//        if "dyson" == searchText.lowercased() {
        if "dyson".hasPrefix(searchText.lowercased()) {
            print("load dyson")
            return pageIndex == 0 ? (MockHttpServer.respIndex1, true) : (MockHttpServer.respIndex2, false)
        } else {
            print("load nothing")
            return (nil, false)
        }
    }
    
    static let respIndex1: Data? = """
            [
                {
                    "id": 0,
                    "name": "Dyson",
                    "kinds": [
                        {
                            "id": 0,
                            "name": "Vacuume",
                            "products": [
                                {
                                    "id": 0,
                                    "name": "V11",
                                    "price": 599.99,
                                    "inStock": true
                                },
                                {
                                    "id": 9,
                                    "name": "V10",
                                    "price": 399.99,
                                    "inStock": false
                                }
                            ]
                        },
                        {
                            "id": 5,
                            "name": "Hair Dryer",
                            "products": [
                                {
                                    "id": 15,
                                    "name": "Supersonic",
                                    "price": 399.99,
                                    "inStock": true
                                }
                            ]
                        }
                    ]
                }
            ]
            """.data(using: .utf8)
    
    static let respIndex2: Data? = """
            [
                {
                    "id": 3,
                    "name": "Dyson",
                    "kinds": [
                        {
                            "id": 30,
                            "name": "Airwrap",
                            "products": [
                                {
                                    "id": 50,
                                    "name": "W21",
                                    "price": 159.99,
                                    "inStock": true
                                }
                            ]
                        }
                    ]
                }
            ]
            """.data(using: .utf8)
}

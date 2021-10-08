//
//  MockHttpServer.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import Foundation

enum MockHttpResponse {
    static let json0: String = """
            {
                "hasMore": true,
                "vendors": [
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
            }
            """
    
    static let json1: String = """
            {
                "hasMore": false,
                "vendors": [
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
            }
            """
    
    static let json2: String = """
            {
                "hasMore": false,
                "vendors": []
            }
            """
}

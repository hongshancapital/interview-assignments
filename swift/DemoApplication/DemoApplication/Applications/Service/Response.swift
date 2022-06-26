//
//  Response.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import Foundation

//
struct Response<T: Decodable>: Decodable {
    let count: Int
    let items: [T]
    
    enum CodingKeys: String, CodingKey {
        case count     = "resultCount"
        case items     = "results"
    }
}

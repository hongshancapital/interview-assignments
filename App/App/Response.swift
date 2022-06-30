//
//  Response.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import Foundation

struct Response: Codable {
    let resultCount: Int
    let results: [ResponseResult]

    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}

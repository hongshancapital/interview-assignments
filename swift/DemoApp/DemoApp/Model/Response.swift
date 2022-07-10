//
//  Response.swift
//  DemoApp
//
//  Created by Gao on 2022/7/10.
//

import Foundation

struct Response: Decodable {
    let total: Int
    let data: [AppModel]
    let pageIndex: Int
    let PageSize: Int
}

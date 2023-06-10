//
//  ApiResponse.swift
//  DemoApp
//
//  Created by liang on 2022/5/25.
//

import Foundation

struct ApiResponse: Decodable {
    let resultCount: Int
    let results: [AppModel]
}

//
//  AppleResultData.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Foundation

struct AppleResultData: Decodable {
    var resultCount: Int = 0
    var results: [AppleData] = []
}

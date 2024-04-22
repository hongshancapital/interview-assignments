//
//  AppleNetResultData.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Foundation

struct AppleNetResultData: Decodable {
    var resultCount: Int = 0
    var results: [AppleNetData] = []
}

//
//  MockModel.swift
//  SCDT
//
//  Created by Zhao Sam on 2022/8/5.
//

import Foundation

struct MockModel: Hashable, Decodable {
    var resultCount: Int
    var results: [ApplicationModel]
}

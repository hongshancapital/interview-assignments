//
//  ResponseModel.swift
//  ListProject
//
//  Created by shencong on 2022/6/12.
//

import Foundation
import SwiftUI

struct ResponseModel: Decodable {
    let resultCount: Int
    let results: [ItemModel]

    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}

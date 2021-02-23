//
//  APIResult.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation

public struct APIResult<Data: Codable> {
    var code: Int
    var msg: String
    var data: Data?
}

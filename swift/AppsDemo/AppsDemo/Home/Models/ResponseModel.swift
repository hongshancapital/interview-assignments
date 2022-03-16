//
//  ResponseModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import Foundation

struct ResponseModel<T: Codable>: Codable {
    let resultCount: Int
    let results: T
}

//
//  ResultModel.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/11.
//

import Foundation

struct ResultModel<T>: Codable where T: Codable {
    var resultCount: Int
    var results: T
}

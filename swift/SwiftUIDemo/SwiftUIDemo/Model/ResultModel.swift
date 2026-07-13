//
//  ResultModel.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import Foundation

struct ResultModel<T>: Codable where T: Codable {
    var resultCount: Int
    var results: T
}

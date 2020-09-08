//
//  Category.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import Foundation

struct Category: Identifiable, Decodable {
    var id: Int
    var brand: String
    var name: String
    var items: [Item]
}

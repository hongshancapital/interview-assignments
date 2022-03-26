//
//  File.swift
//  SwiftUIDemo
//
//  Created by didi_qihang on 2022/3/13.
//

import Foundation

// MARK: - ItemListModel
struct ItemListModel: Codable {
    let code: Int
    let msg: String
    let items: [Item]
}

// MARK: - Item
struct Item: Codable, Identifiable {
    let id = UUID()
    let headURL, title, subTitle: String

    enum CodingKeys: String, CodingKey {
        case headURL = "head_url"
        case title
        case subTitle = "sub_title"
    }
}

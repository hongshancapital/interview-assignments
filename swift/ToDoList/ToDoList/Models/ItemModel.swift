//
//  ItemModel.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/14.
//

import Foundation

struct ItemModel: Hashable, Codable {
    
    public let title: String
    public var isChecked: Bool
    
    public init(_ title: String, _ isChecked: Bool) {
        self.title = title
        self.isChecked = isChecked
    }
}

extension ItemModel {
    func hash(into hasher: inout Hasher) {
        hasher.combine(title)
        hasher.combine(isChecked)
    }
    
    public static func == (lhs: ItemModel, rhs: ItemModel) -> Bool {
        return lhs.title == rhs.title && lhs.isChecked == rhs.isChecked
    }
    
    enum CodingKeys: String, CodingKey {
        case title
        case isChecked
    }

    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(title, forKey: .title)
        try container.encode(isChecked, forKey: .isChecked)
    }
    
    init(from decoder: Decoder) throws{
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.title = try container.decode(String.self, forKey:.title )
        self.isChecked = try container.decode(Bool.self, forKey:.isChecked )
    }
}

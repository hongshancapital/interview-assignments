//
//  GroupModel.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/14.
//

import Foundation

struct GroupModel: Hashable, Codable {
    
    public var groupTitle: String
    public var items: [ItemModel]
    
    public init(_ group: String, _ items: [ItemModel]) {
        self.groupTitle = group
        self.items = items
    }
}

extension GroupModel {
    func hash(into hasher: inout Hasher) {
        hasher.combine(groupTitle)
        hasher.combine(items)
    }
    
    public static func == (lhs: GroupModel, rhs: GroupModel) -> Bool {
        return lhs.groupTitle == rhs.groupTitle && lhs.items == rhs.items
    }
    
    enum CodingKeys: String, CodingKey {
        case groupTitle
        case items
    }

    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(groupTitle, forKey: .groupTitle)
        try container.encode(items, forKey: .items)
    }
    
    init(from decoder: Decoder) throws{
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.groupTitle = try container.decode(String.self, forKey:.groupTitle )
        self.items = try container.decode([ItemModel].self, forKey:.items )
    }
}

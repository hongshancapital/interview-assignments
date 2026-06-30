//
//  ListItem.swift
//  SwiftUIDemo
//
//  Created by HBC on 2022/2/18.
//

import Foundation

struct ListItem : Identifiable, Equatable {
    var id = UUID()
    var name : String
    var image : String
    
    static func == (lhs: ListItem, rhs: ListItem) -> Bool {
        return lhs.id == rhs.id
    }
}

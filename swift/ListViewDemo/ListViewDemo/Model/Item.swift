//
//  Item.swift
//  ListViewDemo
//
//  Created by sky on 2022/9/30.
//

import SwiftUI

struct Item: Identifiable {
    let id = UUID().uuidString
    let name: String
    let desc: String
    var image: String
    var isFavorite: Bool
    
    mutating func reverseFavorite(){
        isFavorite = !isFavorite
    }
}

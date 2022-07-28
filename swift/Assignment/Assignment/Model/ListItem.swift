//
//  ListItem.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import Foundation

typealias ListItem = Goods.ListItem

struct Goods {
  let resultCount: Int
  
  var results: [ListItem]
}

extension Goods {
  struct ListItem {
    private let trackId: Int
    let artworkUrl60: URL
    let trackName: String
    let description: String
    
    @DefaultFalse
    var isFavorite = false
  }
}

extension Goods.ListItem: Identifiable {
  var id: Int { trackId }
}
extension Goods.ListItem: Equatable {}
extension Goods.ListItem: Decodable {}
extension Goods: Decodable {}

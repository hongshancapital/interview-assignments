//
//  SearchSectionModel.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

class SearchSectionModel: Hashable, Codable, Identifiable {
   
    var category: String = ""
    var index: Int = 0
    var id: Int {
        return index
    }
    var list: [SearchResultModel] = []
    
    static func == (lhs: SearchSectionModel, rhs: SearchSectionModel) -> Bool {
        return lhs.category == rhs.category
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(index)
    }
}

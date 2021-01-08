//
//  Action.swift
//  SearchBar-SwiftUI
//
//  Created by evan on 2021/1/6.
//

import Foundation

enum SearchAction {
    case searchResult(series: [Brands.Series])
    case search(text: String)
    case cancel
}

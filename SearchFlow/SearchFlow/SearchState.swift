//
//  State.swift
//  SearchBar-SwiftUI
//
//  Created by evan on 2021/1/6.
//

import Foundation
import Combine

struct SearchState {    
    var searchText: String = ""
    var isSearching: Bool = false
    var didSearch: Bool = false
    var searchResult: [Brands.Series] = []
    
    var isEmpty: Bool { return searchResult.isEmpty && isSearching && !searchText.isEmpty && didSearch }
    
    mutating func clean() {
        searchText = ""
        isSearching = false
        searchResult = []
        didSearch = false
    }
}

extension Store {
    static func reducer(state: inout SearchState, action: SearchAction, server: SearchSever) -> AnyPublisher<SearchAction, Never> {
        switch action {
        case .searchResult(series: let series): state.searchResult = series
        case .search(text: let text):
            state.didSearch = true
            return server.service
                .searchPublisher(matching: text)
                .replaceError(with: [])
                .map { SearchAction.searchResult(series: $0) }
                .eraseToAnyPublisher()
        case .cancel: state.clean()
        }
        return Empty().eraseToAnyPublisher()
    }
}

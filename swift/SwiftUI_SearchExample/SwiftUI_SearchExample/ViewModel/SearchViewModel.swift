//
//  SearchViewModel.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation
import SwiftUI

class SearchViewModel: ObservableObject {
    
    /// 搜索数据
    @Published var searchList: [SearchModel] = []
    
    /// 搜索关键字
    @Published var searchKeyword = ""
    
    /// 搜索页数
    @Published var searchIndex = 0
    
    init() {
        $searchKeyword
            .debounce(for: .seconds(0.1), scheduler: RunLoop.main)
            .flatMap({ _ in
                SearchRequest.dataTaskPublisher(keyword: self.searchKeyword, index: 0)
            })
            .tryMap {
                try? JSONDecoder().decode(Response<[SearchModel]>.self, from: $0.data)
            }
            .map({ $0?.data ?? [] })
            .replaceError(with: [])
            .receive(on: RunLoop.main)
            .assign(to: &$searchList)
        $searchIndex
            .debounce(for: .seconds(1), scheduler: RunLoop.main)
            .flatMap({ _ in
                SearchRequest.dataTaskPublisher(keyword: self.searchKeyword, index: self.searchIndex)
            })
            .tryMap {
                try? JSONDecoder().decode(Response<[SearchModel]>.self, from: $0.data)
            }
            .map({ self.searchIndex != 0 ? self.searchList + ($0?.data ?? []) : $0?.data ?? [] })
            .replaceError(with: [])
            .receive(on: RunLoop.main)
            .assign(to: &$searchList)
    }
    
    func isFirst(data: SearchModel) -> Bool {
        return searchList.first == data
    }
    
    func isLast(data: SearchModel) -> Bool {
        return searchList.last == data
    }
    
}

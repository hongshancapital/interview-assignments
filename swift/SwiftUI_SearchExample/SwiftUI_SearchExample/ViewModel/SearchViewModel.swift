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
    
    var isNotMoreData = false
    
    init() {
        /// SearchKeyword index默认一直都是0 因为在调整searchKeyword时，我们需要refresh数据
        $searchKeyword
            .debounce(for: .seconds(0.1), scheduler: RunLoop.main)
            .flatMap({ _ in
                SearchRequest.dataTaskPublisher(keyword: self.searchKeyword, index: 0)
            })
            .tryMap {
                try? JSONDecoder().decode(Response<[SearchModel]>.self, from: $0.data)
            }
            .map({
                let data = $0?.data ?? []
                if data.count == 0 || data.count < 10 {
                    self.isNotMoreData = true
                } else {
                    self.isNotMoreData = false
                }
                return data
            })
            .replaceError(with: [])
            .receive(on: RunLoop.main)
            .assign(to: &$searchList)
        /// SearchIndex需要根据Index在map()中数据返回的数据
        $searchIndex
            .debounce(for: .seconds(1), scheduler: RunLoop.main)
            .flatMap({ _ in
                SearchRequest.dataTaskPublisher(keyword: self.searchKeyword, index: self.searchIndex)
            })
            .tryMap {
                try? JSONDecoder().decode(Response<[SearchModel]>.self, from: $0.data)
            }
            .map({
                let data = $0?.data ?? []
                if data.count == 0 || data.count < 10 {
                    self.isNotMoreData = true
                } else {
                    self.isNotMoreData = false
                }
                return self.searchIndex != 0 ? self.searchList + data : data
            })
            .replaceError(with: [])
            .receive(on: RunLoop.main)
            .assign(to: &$searchList)
    }
    
}

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
    
    init() {
        $searchKeyword
            .debounce(for: .seconds(0.1), scheduler: RunLoop.main)
            .flatMap({_ in
                SearchRequest.dataTaskPublisher(keyword: self.searchKeyword)
            })
            .tryMap {
                try? JSONDecoder().decode(Response<[SearchModel]>.self, from: $0.data)
            }
            .map({ $0?.data ?? [] })
            .replaceError(with: [])
            .receive(on: RunLoop.main)
            .assign(to: &$searchList)
    }
    
}

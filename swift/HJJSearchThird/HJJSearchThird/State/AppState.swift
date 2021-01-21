//
//  AppState.swift
//  HJJLogin
//
//  Created by haojiajia on 2021/1/18.
//

import Foundation
import SwiftUI
import Combine

struct AppState {
    var isLoadingGoods = false
    var loadGoodsError: AppError?
    var filterGoods = [GoodsModel]()
    
    class GoodsChecker {
        @Published var goods: [GoodsModel]?
        @Published var searchText: String = ""
        
        var filteredPublisher: AnyPublisher<[GoodsModel], Never> {
            let filteredGoods = $searchText
                    .debounce(for: .milliseconds(500), scheduler: DispatchQueue.main)
                    .removeDuplicates()
                    .compactMap {($0)} //去除空字符串
                    .map { $0.lowercased() }//转小写
                    .flatMap { searchedText -> AnyPublisher<[GoodsModel], Never> in
                        let filterGoods = self.goods?.filter { (goods) -> Bool in
                            searchedText.isEmpty ? true : goods.brand.lowercased().contains(searchedText)
                        }
                        return Just(filterGoods ?? [GoodsModel]()).eraseToAnyPublisher()
                }
            return filteredGoods.eraseToAnyPublisher()
        }
        
    }
    
    var goodsChecker = GoodsChecker()
}


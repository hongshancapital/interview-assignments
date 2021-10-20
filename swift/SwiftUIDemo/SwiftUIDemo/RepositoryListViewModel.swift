//
//  RepositoryListViewModel.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation
import SwiftUI
import Combine
  
enum ViewModelState {
    case isLoading , showData, isStartInput, noData
}

/// 网络API类
class RepositoryListViewModel: ObservableObject {
    
    typealias SearchRepositories = (String) -> AnyPublisher<Result<[SearchResultModel], ErrorResponse>, Never>
    
    private let _searchWithQuery = PassthroughSubject<String, Never>()
    private var cancellables: [AnyCancellable] = []
    // 原始数据，没有分组的
    var originalRepositories: [SearchResultModel] = []
    /// 分组后的数据
    @Published var repositorySection: [SearchSectionModel] = []
    @Published private(set) var errorMessage: String? = nil
    /// 管理view当前状态的
    @Published  var state: ViewModelState = .isStartInput
    @Published  var searchKey: String = ""
    // 保留历史搜索记录 最多十条，里面有去重，最近的关键字在最尾部
    @Published private(set) var historyKeys: [String] = []
    /// 分组的字典，string是分组的名字 ，Int是分组在repositorySection的位置，提高取的i效率
    var categoryKeys: [String: Int] = [:]
    
    
    init<S: Scheduler>(searchRepositories: @escaping SearchRepositories = RepositoryAPI.search, mainScheduler: S) {
        
        let searchTrigger = _searchWithQuery
            .filter { !$0.isEmpty }
            .debounce(for: .milliseconds(300), scheduler: mainScheduler)
        
        let response = searchTrigger
            .flatMapLatest { query -> AnyPublisher<([SearchResultModel], String?), Never> in
                searchRepositories(query)
                    .map {[weak self] result -> ([SearchResultModel], String?) in
                        guard let self = self else {  return ([], "self dinit") }
                        switch result {
                        case let .success(repositories):
                            DispatchQueue.main.async {
                                self.handleHistorySearchKey()
                            }
                            // 在这里处理假数据，用本地的json数据，真实情况下此处要去掉
                            let result: [SearchResultModel] = load("Data.json")
                            return (result, nil)
                        case let .failure(response):
                            return ([], response.message)
                        }
                    }
                    .eraseToAnyPublisher()
            }
            .receive(on: mainScheduler)
            .share()

        response.map {[unowned self] res in
            if (res.1 != nil) {
                return ViewModelState.noData
            } else {
                if self.searchKey == "Dysonx" {
                    return ViewModelState.showData
                } else if self.searchKey.lengthOfBytes(using: String.Encoding.utf8) == 0 {
                    return ViewModelState.isStartInput
                } else {
                    return ViewModelState.noData
                }
            }
        }.assign(to: \.state, on: self).store(in: &cancellables)
        
        
        response.sink {[unowned self] (list, str) in
            // 把列表的数据进行分组处理
            // 把所有的categoryKeys 数组种，然后去重
            // 根据 categoryKeys 生成外层section数组
            // 把 original数组 依次遍历组合到到section中
            self.originalRepositories.append(contentsOf: list)

            self.repositorySection.removeAll()
            var tempSet = Set<String>()
            self.originalRepositories.forEach { item in
                tempSet.insert(item.category)
            }
            let tempCategoryArray = tempSet.sorted()
            tempCategoryArray.enumerated().forEach { (index, element) in
                self.categoryKeys.updateValue(index, forKey: element)
            }
            self.categoryKeys.enumerated().forEach { (key, value) in
                let section = SearchSectionModel()
                section.category = value.key
                section.index = key
                self.repositorySection.append(section)
            }
            self.originalRepositories.forEach { singleModel in
                let index: Int? = self.categoryKeys[singleModel.category]
                if let _index = index {
                    let section = self.repositorySection[_index]
                    section.list.append(singleModel)
                }
            }

        }.store(in: &cancellables)
 
        response
            .map { $0.1 }
            .assign(to: \.errorMessage, on: self)
            .store(in: &cancellables)
    }
    
    func search() {
        debugPrint("searchKey : \(searchKey)")
        state = .isLoading
        _searchWithQuery.send(searchKey)
    }
    
    func searchNextPage() {
        debugPrint("下一页")
        _searchWithQuery.send(searchKey)
    }
    /// 清空数据和默认状态
    func clear() {
        state = .isStartInput
        originalRepositories.removeAll()
        repositorySection.removeAll()
    }
    
    func handleHistorySearchKey() {
        
        if !self.historyKeys.contains(self.searchKey) {
            self.historyKeys.append(self.searchKey)
        } else {
            // 保持用户最新输入搜索的文字在最尾部
            let index = self.historyKeys.firstIndex(of: self.searchKey)
            guard let newIndex = index else {
                return
            }
            self.historyKeys.remove(at: newIndex)
            self.historyKeys.append(self.searchKey)
        }
        
        if self.historyKeys.count > 10 {
            self.historyKeys.remove(at: 10)
        }
        
    }
}



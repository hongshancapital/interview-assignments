//
//  ListViewModel.swift
//  AppList
//
//  Created by mengyun on 2022/3/19.
//

import Foundation
import SwiftUI
import Combine

class ListViewModel: ObservableObject {
    
    var pageSize: Int = 20
    var isRefresh: Bool = false
    var favs: [String: Bool] = [:]
    
    @Published var datas: [ListCellModel] = []
    @Published var loadingState: LoadingState = .PreLoading
    
    private var loadMoreSubject = CurrentValueSubject<Void, Never>(())
    private var cancellableSet: Set<AnyCancellable> = []
    private let FavoritesSaveKey = "FavoritesSaveKey"
    
    init() {
        fetchFavoriteData()
    }
    
    // 下拉刷新
    func refreshDatas() {
        // 取消之前的订阅
        cancellableSet.removeAll()
        isRefresh = true
        subscriptionList()
    }
    
    // 上拉加载更多
    func loadMore() {
        loadMoreSubject.send()
    }
    
    // 订阅列表数据
    func subscriptionList () {
        guard let url = Bundle.main.url(forResource: "mock", withExtension: "txt") else { return }
        guard let data = try? Data(contentsOf: url) else { return }
        guard let lisModel = try? JSONDecoder().decode(ListModel.self, from: data)  else { return }
        
        Just(lisModel.results)
            .eraseToAnyPublisher()
            .flatMap { $0.publisher }
            .collect(pageSize)
            .zip(loadMoreSubject)
            .receive(on: RunLoop.main)
            .handleEvents(
                receiveOutput: { [weak self] _ in
                    self?.loadingState = .Loading
                })
            .sink { [weak self] completion in
                switch completion {
                case .finished:
                    self?.loadingState = .LoadComplete
                case .failure(let error):
                    print("Error--: \(error)")
                }
            } receiveValue: { [weak self] value in
                guard let sf = self else { return }
                // print("receiveValue--:", value.0.count, sf.loadingState)
                if !value.0.isEmpty {
                    if sf.isRefresh {
                        sf.datas = Array(value.0)
                    } else {
                        sf.datas.append(contentsOf: value.0)
                    }
                    sf.isRefresh = false
                    sf.loadingState = .LoadMore
                }
            }
            .store(in: &cancellableSet)
    }
}

// MARK: - 获取、更新 like 状态
extension ListViewModel {
    
    func fetchFavoriteData() {
        if let favs = UserDefaults.standard.object(forKey: FavoritesSaveKey) as? [String: Bool] {
            self.favs = favs
        }
    }
    
    func saveFavoriteData() {
        UserDefaults.standard.set(favs, forKey: FavoritesSaveKey)
    }
}


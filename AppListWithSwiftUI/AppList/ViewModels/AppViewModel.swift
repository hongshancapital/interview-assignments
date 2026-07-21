//
//  AppViewModel.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//

import SwiftUI
import Foundation

@MainActor
final class AppViewModel: ObservableObject {
    private var mockDatas  = MockDatas()
    
    @Published private var loadingState: LoadingState = .none
    @Published var isRefreshing = false
    @Published var datas: [DataModel] = []
    
    private(set) var dataState = DataState.none
    private(set) var page = 0
    private(set) var pageLength = 25
    
    func fetchGetResult(state: LoadingState) async {
        if (state == .none) {
            return
        }
        
        loadingState = state
        page =  0
        switch(state){
        case .firstLoading , .refreshing:
            page = 0
            break
            
        case .loadingMore:
            page = page + 1
        default:
            break
        }
        try? await Task.sleep(nanoseconds: 2 * 1_000_000_000)
        
        let arr = mockDatas.load(page: page, pageLength: pageLength)
        if (page == 0){
            datas.removeAll()
        }
        datas.append(contentsOf: arr)
        if mockDatas.isEnd(currentCount: datas.count){
            dataState = .noMore
        }else if (datas.count > 0){
            dataState = .hasMore
        }else{
            dataState = .none
        }
        loadingState = .done
        isRefreshing = false
    }
    
    func firstLoad() async {
        guard loadingState != .none || loadingState != .done else { return }
        guard isRefreshing != true else { return }
        await fetchGetResult(state: .firstLoading)
    }
    
    func refresh() async {
        guard loadingState != .none || loadingState != .done else { return }
        guard isRefreshing != true else { return }
        isRefreshing = true
        await fetchGetResult(state: .refreshing)
    }
    
    func loadMore() async {
        guard loadingState != .none || loadingState != .done else { return }
        guard isRefreshing != true else { return }
        guard dataState == .hasMore else { return }
        await fetchGetResult(state: .loadingMore)
    }
    
    var  hasMoreData: Bool{
        return dataState == .hasMore;
    }
    
    var  noMoreData: Bool{
        return dataState == .noMore;
    }
    
    func like(model: DataModel){
        mockDatas.like(model: model)
    }
}

enum LoadingState: Equatable {
    case none
    case firstLoading
    case refreshing
    case loadingMore
    case done
}

enum DataState: Equatable {
    case none
    case hasMore
    case noMore
}



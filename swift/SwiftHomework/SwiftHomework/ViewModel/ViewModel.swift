//
//  ViewModel.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/14.
//

import Foundation
import Combine
import UIKit

class ViewModel: ObservableObject {
    //请求的总数据
    @Published var currentList: AppList = []
    @Published var allAppList: AppList?
    @Published var isNoMoreData:Bool = false
    //已经展示的
    let service = JXNetworkService.shared;
    var totalCount = 0
    var cancellable = Set<AnyCancellable>()
    
    init() {
        refreshData()
    }
    
    func refreshData() {
        service.getChatAppList()
            .receive(on: RunLoop.main)
            .sink { completion in
                print(completion)
            } receiveValue: { [weak self] data in
                self?.currentList = []
                self?.allAppList = data.results;
                self?.totalCount = data.results.count;
                _ = self?.loadMore()
            }
            .store(in: &cancellable)
    }
    
    //返回false 为无更多数据
    func loadMore(){
        guard let appInfoList = self.allAppList else { return }
        var nextCount = self.currentList.count + 10;
        nextCount = nextCount > totalCount ? totalCount : nextCount;
        if(nextCount == self.currentList.count){
            isNoMoreData = true
            return
        }
        var a = currentList;
        for i in self.currentList.count ... (nextCount - 1) {
            a.append(appInfoList[i])
        }
        currentList = a;
        isNoMoreData = false
    }
}

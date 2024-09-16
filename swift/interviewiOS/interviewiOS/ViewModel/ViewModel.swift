//
//  ViewModel.swift
//  interviewiOS
//
//  Created by aa on 2022/5/4.
//
import Foundation

class ViewModel: ObservableObject {
    @Published var items: [AppInfo] = []
    
    private let max: Int = 50
    private let countPerPage: Int = 8
    var appList = APPListInfo
    
    init() {
        print("items.count = \(items.count)")
    }
}

extension ViewModel {
    
    var canLoadMore: Bool {
        return items.count < max
    }
    
    func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) { [weak self] in
            self?.appendItems()
        }        
    }
    
}

private extension ViewModel {
    
    func appendItems() {
        let currentCount = items.count
        let lastItemIndex = appList.count - currentCount >= 8 ? 8 : appList.count - currentCount
        let slice:ArraySlice=appList[currentCount...(currentCount + lastItemIndex - 1)]
        items.append(contentsOf: slice)
    }
    
}

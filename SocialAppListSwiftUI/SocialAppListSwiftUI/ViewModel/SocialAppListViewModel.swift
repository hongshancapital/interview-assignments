//
//  SocialAppListViewModel.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import Foundation
import SwiftUI

@MainActor
final class SocialAppListViewModel: ObservableObject {
    
    private(set) var pageSize = 20
    private lazy var dumpData = MockData.getData()
    
    @Published private(set) var isFirstLoading = true
    @Published private(set) var refreshState: LoadingState = .unintialized
    @Published private(set) var loadingMoreState: LoadingState = .unintialized
    
    @Published var appList : [SocialAppModel] = []
    
    // MARK - Initialization
    
    init() {
        
    }
    
    init(pageSize: Int) {
        self.pageSize = pageSize
    }
    
    // MARK - Public
    
    var isHasMore: Bool {
        return appList.count < dumpData.resultCount
    }
    
    func refresh() async {
        guard refreshState != .loading else { return }

        refreshState = .loading
        
        // Delay 1 second:
        try? await Task.sleep(nanoseconds: 1_000_000_000)
        
        // test network error
        // refreshState = .error(LoadError.noNetWork)
        
        let toIndex = min(dumpData.resultCount, pageSize)
        appList = Array(dumpData.results[..<toIndex])

        refreshState = .succeed
        isFirstLoading = false
    }
    
    func loadMore() async {
        guard refreshState == .succeed else { return }
        guard loadingMoreState != .loading else { return }
        guard isHasMore else { return }
        
        loadingMoreState = .loading
        
        // Delay 1 second:
        try? await Task.sleep(nanoseconds: 1_000_000_000)

        let fromIndex = min(appList.count, dumpData.resultCount)
        let toIndex = min(self.appList.count + pageSize, dumpData.resultCount)
        appList.append(contentsOf: dumpData.results[fromIndex..<toIndex])
        
        loadingMoreState = .succeed
    }
    
}

extension SocialAppListViewModel {
    
    enum LoadingState: Equatable {
        case unintialized
        case loading
        case error(_ error: Error)
        case succeed
                
        public static func == (lhs: Self, rhs: Self) -> Bool {
            if case .unintialized = lhs,
                case .unintialized = rhs {
                return true
            } else if case .loading = lhs,
                case .loading = rhs {
                return true
            } else if case .error = lhs,
                case .error = rhs {
                return true
            } else if case .succeed = lhs,
                case .succeed = rhs {
                return true
            }
            return false
        }
    }
    
    enum LoadError : Error {
        case noNetWork
    }
    
}

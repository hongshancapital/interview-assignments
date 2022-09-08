//
//  ViewModel.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import Foundation
import Combine
import Reachability

class ViewModel: ObservableObject {
    enum LoadingState {
        case idle
        case loading
        case loaded
    }
    
    @Published private(set) var itemsData: [CellModel] = []
    
    private var pageNum: Int = 1
    
    var loadingState: LoadingState = .loading
    
    private let reachability = try! Reachability()
    
    private var cancellables = Set<AnyCancellable>()
    
    var hasMoreData = true
    
    init() {
        networkMonitor()
        NotificationCenter.default.publisher(for: Notification.Name("HasMoreData"))
            .sink(receiveValue: { [self] notification in
                hasMoreData = (notification.object as! Bool)
            })
            .store(in: &cancellables)
    }
    
    // MARK: - Monitor network servicee
    private func networkMonitor() -> Void {
        reachability.whenReachable = { [weak self] reachability in
            if reachability.connection == .unavailable {
                self?.loadingState = .idle
            } else {
                self?.loadingState = .loading
            }
        }
        
        do {
            try reachability.startNotifier()
        } catch {
            print("Failed to monitor network service.")
        }
    }
    
    // MARK: - Data operation
    func loadData() -> Void {
        if reachability.connection == .unavailable {
            loadingState = .idle
            return
        }
        
        loadingState = .loading
        
        Just(DataProvider.fetchData(pageNum: self.pageNum))
            .sink { items in
                DispatchQueue.global().asyncAfter(deadline: .now() + 1) {
                    DispatchQueue.main.async { [weak self] in
                        self?.itemsData = items
                        self?.pageNum += 1
                        self?.loadingState = .loaded
                    }
                }
            }
            .store(in: &cancellables)
    }
    
    func reload() -> Void {
        pageNum = 1
        
        loadData()
    }
    
    func favorite(item: CellModel, success: @escaping () -> Void, failure: @escaping (_ error: Error) -> Void) -> Void {
        DataProvider.changeFavorToFile(model: item, success: success, failure: failure)
    }
    
    deinit {
        do {
            try reachability.startNotifier()
        } catch {
            print("Failed to stop monitor.")
        }
    }
}

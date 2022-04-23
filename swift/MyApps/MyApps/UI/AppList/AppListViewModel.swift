//
//  AppListViewModel.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation
import Combine

let PageCount: Int = 20

class AppListViewModel: ObservableObject {
    var sub: AnyCancellable?
    enum LoadingState {
        case notRequested
        case loading
        case loaded
        case noMoreData
        case failed(Error)
    }
    
    var service: AppInfoServiceProtocol
    
    @Published var viewModels: [AppInfoViewModel]?
    @Published var state: LoadingState = .notRequested
    
    init(service: AppInfoServiceProtocol) {
        self.service = service
    }
    
    func loadMyApps() {
        
        if state == .loading {
            return
        }
        state = .loading
        //cancel the previous subscribe
        sub?.cancel()
        sub = service.fetchMyApps(startIndex: 0, count: PageCount).sink { subscribersCompletion in
            if let error = subscribersCompletion.error {
                self.state = .failed(error)
            }
        } receiveValue: { apps in
            self.viewModels = apps.map { app in
                AppInfoViewModel(app: app)
            }
            self.state = .loaded
        }
    }
    
    func loadMoreApps() {
        
        if state == .loading {
            return
        }
        state = .loading
        
        //cancel the previous subscribe
        sub?.cancel()
        sub = service.fetchMyApps(startIndex: viewModels?.count ?? 0, count: PageCount).sink { subscribersCompletion in
            if let error = subscribersCompletion.error {
                self.state = .failed(error)
            }
        } receiveValue: { apps in
            
            self.viewModels?.append(contentsOf: apps.map({ app in
                AppInfoViewModel(app: app)
            }))                        
            
            if (apps.count < PageCount) {
                self.state = .noMoreData
            } else {
                self.state = .loaded
            }
        }
    }
    
}


extension AppListViewModel.LoadingState: Equatable {
    static func == (lhs: Self, rhs: Self) -> Bool {
        switch (lhs, rhs) {
        case (.notRequested, .notRequested): return true
        case (.loading, .loading): return true
        case (.loaded, .loaded): return true
        case (.noMoreData, .noMoreData): return true
        case let (.failed(lhs), .failed(rhs)): return lhs.localizedDescription == rhs.localizedDescription
        
        default: return false
        }
    }
}

extension Subscribers.Completion {
    var error: Failure? {
        switch self {
        case let .failure(error): return error
        default: return nil
        }
    }
}

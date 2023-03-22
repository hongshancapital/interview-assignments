//
//  SearchListViewModel.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
import Combine
import os.log

class SearchListViewModel: ObservableObject {
    @Published private(set) var state = PageState.idle
    
    enum PageState: Equatable {
        static func == (lhs: PageState, rhs: PageState) -> Bool {
            switch(lhs, rhs) {
            case(.idle, .idle):
                return true
            case(.failed(let lhsError), .failed(let rhsError)):
                return lhsError == rhsError
            case(.loaded, .loaded):
                return true
            case(.loadingMore, .loadingMore):
                return true
            case(.refreshing, .refreshing):
                return true
            default:
                return false
            }
        }
        
        case idle
        case failed(ErrorType)
        case loaded
        case loadingMore
        case refreshing
        
    }
    private let limit = 30
    @Published var items: [DataModel.AppItem] = []
    @Published var listFull = false
    
    @Published private(set) var savedItems: Set<Int> = []
    
    private var subscriptions: Set<AnyCancellable> = []
    
    private let networking: SearchAppAPIProtocol
    
    private var db = Database()
    
    init(networking: SearchAppAPIProtocol) {
        self.savedItems = db.load()
        self.networking = networking
    }
    
    func searchAppItems(_ loadMore: Bool) {
        networking.searchApp(quary: "chat", limit: limit, offset: loadMore ? items.count: 0)
            .sink {[weak self] completion in
                guard let self = self else { return }
                switch completion {
                case .failure(let error):
                    Logger.network.debug("searchApp error: \(error.localizedDescription)")
                    if let code = error.responseCode {
                        self.state = .failed(.backend(code))
                    }else if error.isSessionTaskError {
                        self.state = .failed(.noInternet)
                    }else if error.isResponseSerializationError {
                        self.state = .failed(.decoding)
                    } else {
                        self.state = .failed(.unknown)
                    }
                case .finished:
                    break
                }
            } receiveValue: {[weak self] value in
                guard let self = self else { return }
                self.state = .loaded
                self.items = loadMore ? (self.items + value.results) : value.results
                if value.resultCount < self.limit {
                    self.listFull = true
                } else {
                    self.listFull = false
                }
            }
            .store(in: &subscriptions)
    }
    
    func loadMoreAppItems() {
        state = .loadingMore
        searchAppItems(true)
    }
    
    func refreshAppItems() {
        state = .refreshing
        searchAppItems(false)
    }
    
    func contains(_ item: DataModel.AppItem) -> Bool {
        return savedItems.contains(item.trackId)
    }
    
    // Toggle saved items
    func toggleCollect(_ item: DataModel.AppItem) {
        if savedItems.contains(item.trackId) {
            savedItems.remove(item.trackId)
        } else {
            savedItems.insert(item.trackId)
        }
        db.save(items: savedItems)
    }
}

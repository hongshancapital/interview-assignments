//
//  AppListViewModel.swift
//  AppStore
//
//  Created by Ma on 2022/3/13.
//

import Foundation
import Combine

class AppListViewModel: ObservableObject {
    
    @Published var appData: AppData = AppData(resultCount: 0, results: [])
    @Published var errorMessage : String = ""
    @Published var hasMore: Bool = true
    @Published var state : State = .none
    @Published var isHeaderRefreshing: Bool = false
    @Published var isFooterRefreshing: Bool = false
    
    let limit = 10
    let apiService: ApiService
    
    enum State {
        case none
        case initial
        case errorState
        case refreshing
        case loadingMore
    }
    
    enum Event {
        case onInitial
        case onRefresh
        case onLoadMore
    }
    
    private let dataSubject = PassthroughSubject<Event,Never>()
    private let resSubject = PassthroughSubject<AppData,Never>()
    private let errorSubject = PassthroughSubject<ApiServiceError, Never>()
    
    private var cancellable = Set<AnyCancellable>()
    
    init(apiService: ApiService = ApiService()) {
        self.apiService = apiService
        setupInputs()
        setupOutputs()
    }
    
    private func setupInputs() {
        dataSubject
            .map{ [weak self] event -> URLRequest? in
                let offset = (event == .onLoadMore ? self?.appData.results.count : 0)
                self?.state = (event == .onLoadMore ? .loadingMore : .refreshing)
                switch event {
                case .onLoadMore:
                    self?.state = .loadingMore
                    self?.isFooterRefreshing = true
                case .onInitial:
                    self?.state = .initial
                default:
                    self?.isHeaderRefreshing = true
                    self?.state = .refreshing
                }
                return self?.getRequest(offset: offset ?? 0, limit: self?.limit ?? 0)
            }
            .flatMap { [weak self,apiService] in
                apiService
                    .request(req: $0!, res: AppData.self)
                    .catch { [weak self] error -> Empty<AppData,Never> in
                        self?.errorSubject.send(error)
                        return .init()
                    }
            }
            .share()
            .subscribe((resSubject))
            .store(in: &cancellable)
    }
    
    private func setupOutputs() {
        resSubject
            .map { [weak self] appData -> AppData in
                if self?.state == .loadingMore {
                    self?.hasMore = (appData.results.count == self?.limit ?? 10)
                    return AppData(resultCount: (self?.appData.resultCount)! + appData.resultCount, results: (self?.appData.results)! + appData.results);
                }
                else {
                    return appData
                }
            }
            .sink(receiveCompletion: { appData in
                
            }, receiveValue: { appData in
                self.appData = appData
                self.state = .none
                self.isHeaderRefreshing = false
                self.isFooterRefreshing = false
            })
            .store(in: &cancellable)
        
        errorSubject
            .map { error ->String in
                switch error {
                case .networkError:
                    return "网络出错"
                default:
                    return "服务器出错"
                }
            }
            .sink(receiveCompletion: { _ in
                
            }, receiveValue: { errorMsg in
                self.errorMessage = errorMsg
                self.state = .errorState
                self.isHeaderRefreshing = false
                self.isFooterRefreshing = false
            })
            .store(in: &cancellable)
    }
    
    func excute(_ event:Event) {
        guard state == .none || state == .errorState else {
            return
        }
        self.dataSubject.send(event)
    }
    
    // https://itunes.apple.com/search?entity=software&limit=50&term=chat&offset=0
    func getRequest(offset:Int,limit:Int) -> URLRequest {
        let url = URL(string: "https://itunes.apple.com/search")
        var components = URLComponents(url: url!, resolvingAgainstBaseURL: true)!
        components.queryItems = [
            URLQueryItem(name: "entity", value: "software"),
            URLQueryItem(name: "limit", value: String(limit)),
            URLQueryItem(name: "term", value: "chat"),
            URLQueryItem(name: "offset", value: String(offset))
        ]
        return URLRequest(url: components.url!)
    }
}

//
//  AppListViewModel.swift
//  Demo
//
//  Created by jyt on 2023/3/21.
//

import Foundation
import Combine

enum CustomError: Error {
    case unknown
    case networkTimeout
    case networkBadURL
    case invalidData
}

final class AppListViewModel: ObservableObject {
    @Published private(set) var appList: AppItemList = AppItemList(resultCount: 0, results: [])
    
    @Published private(set) var favoriteApps: Set<Int> = []
    
    @Published private(set) var isLoading = false
    private var currentTask: Task<(), Never>?
    
    init() {
        self.favoriteApps = UserDefaults.standard.mutableSetValue(forKey: "favoriteAppIDs") as! Set<Int>
    }
    
    func toggleFavorite(_ id: Int) {
        if favoriteApps.contains(id) {
            favoriteApps.remove(id)
        } else {
            favoriteApps.insert(id)
        }
        UserDefaults.standard.set(Array(favoriteApps), forKey: "favoriteAppIDs")
    }
    
    func isFavorite(_ id: Int) -> Bool {
        return favoriteApps.contains(id)
    }
    
    
    private(set) var customErr: CustomError?
    func handleError(_ err: Error) {

        if err is DecodingError {
            self.customErr = .invalidData
        } else {
            let error = err as NSError 
            if error.domain == NSURLErrorDomain {
                switch error.code {
                case NSURLErrorTimedOut:
                    self.customErr = .networkTimeout
                case NSURLErrorBadURL:
                    self.customErr = .networkBadURL
                case NSURLErrorCancelled:
                    break
                default:
                    self.customErr = .unknown
                }
            }
        }
        
        print(err.localizedDescription)
    }
    
    private let increment = 10
    private var queryCount = 10
    private var queryLimit = 50
    private var queryUrl: URL {
        return URL(string: "https://itunes.apple.com/search?entity=software&limit=\(queryCount)&term=chat")!
    }
    
    var isMore: Bool {
        queryCount < queryLimit
    }
    
    //MARK: - Request
    @MainActor
    private func requestData() async {
        isLoading = true
        defer {
            isLoading = false
        }
        var request = URLRequest(url: queryUrl)
        request.timeoutInterval = 3
        
        do {
            let (data, response) = try await URLSession.shared.data(for: request)
            if let response = response as? HTTPURLResponse, response.statusCode == 200 {
                self.appList = try loadJSON(data)
            } else {
                print(response)
            }
        } catch {
            self.handleError(error)
        }
    }
    
    @MainActor
    func loadMore() {
        if isMore && !isLoading {
            queryCount = min(queryLimit, increment + queryCount)
            currentTask = Task {
                await requestData()
                currentTask = nil
            }
        }
    }
    
    
    func refreshData() async {
        queryCount = 10
        await requestData()
    }
    
    
    //MARK: - Combine Deprecated
//    private var cancellable: AnyCancellable?
//    private var subject: PassthroughSubject<URL, Never> = PassthroughSubject()
//    func requestCombine() {
//        let request = URLRequest(url: queryUrl)
//        self.cancellable = URLSession.shared.dataTaskPublisher(for: request)
//            .map { data, response in
//                data
//            }
//            .decode(type: AppItemList.self, decoder: JSONDecoder())
//            .receive(on: RunLoop.main)
//            .sink {
//                switch $0 {
//                case .finished:
//                    print("Upstream finished.")
//                case .failure(let err):
//                    self.handleError(err)
//                }
//            } receiveValue: { list in
//                self.appList = list
//            }
//    }
//    
//    func request(for url: URL) {
//        self.subject.send(url)
//    }
//    
//    
//    private func configureObservation() {
//        self.cancellable = subject.flatMap { url in
//            URLSession.shared.dataTaskPublisher(for: url)
//        }.map { data, response in
//            data
//        }
//        .decode(type: AppItemList.self, decoder: JSONDecoder())
//        .receive(on: RunLoop.main)
//        .sink {
//            switch $0 {
//            case .finished:
//                print("Upstream finished.")
//            case .failure(let err):
//                self.handleError(err)
//            }
//        } receiveValue: { list in
//            self.appList = list
//        }
//    }
    
}




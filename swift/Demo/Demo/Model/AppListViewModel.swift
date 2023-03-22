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
    
    private let increment = 10
    private var queryCount = 10
    private var queryLimit = 50
    private var queryUrl: URL {
        return URL(string: "https://itunes.apple.com/search?entity=software&limit=\(queryCount)&term=chat")!
    }
    
    var isMore: Bool {
        queryCount < queryLimit
    }
    
    //MARK: - Error Handling
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
    
    //MARK: - Request
    @MainActor
    private func requestData() async {
        isLoading = true
        defer {
            isLoading = false
        }
        let request = URLRequest(url: queryUrl)
        
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
            Task {
                await requestData()
            }
        }
    }
    
    func refreshData() async {
        queryCount = 10
        await requestData()
    }
}

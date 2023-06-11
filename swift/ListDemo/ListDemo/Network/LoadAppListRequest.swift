//
//  LoadAppListRequest.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/10.
//

import Foundation
import Combine

struct LoadAppListRequest {
    static func refresh() -> AnyPublisher<[AppListViewModel], AppError> {
        URLSession.shared.dataTaskPublisher(for: urlForRequest())
                    .tryMap { $0.data }
                    .decode(type: AppItems.self, decoder: JSONDecoder())
                    .tryMap { Array($0.results.suffix(from: 0)) }
                    .map { $0.map { AppListViewModel(app: $0) } }
                    .mapError { AppError.networkingFailed($0) }
                    .receive(on: DispatchQueue.main)
                    .eraseToAnyPublisher()
    }
    
    static func loadMore(pageIndex: Int = 0, pageAppsNum: Int = 20) -> AnyPublisher<[AppListViewModel], AppError> {
        URLSession.shared.dataTaskPublisher(for: urlForRequest(pageIndex * (pageAppsNum + 1)))
                    .tryMap { $0.data }
                    .decode(type: AppItems.self, decoder: JSONDecoder())
                    .tryMap {
                        if $0.results.count > pageIndex * pageAppsNum {
                            return Array($0.results.suffix(from: pageIndex * pageAppsNum ))
                        }
                        return Array()
                    }
                    .map { $0.map { AppListViewModel(app: $0) } }
                    .mapError { AppError.networkingFailed($0) }
                    .receive(on: DispatchQueue.main)
                    .eraseToAnyPublisher()
    }
    
    // https://itunes.apple.com/search?entity=software&limit=50&term=chat
    private static func urlForRequest(_ limit: Int = 0) -> URL {
        debugPrint("🦁️urlForRequest \(limit) ")
        let numberToRequest = min(limit, 50)
        let urlString = """
        https://itunes.apple.com/search?\
        entity=software&limit=\(numberToRequest)&term=chat
        """
        return URL(string: urlString)!
    }
}

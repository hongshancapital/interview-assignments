//
//  AppListRequest.swift
//  AppList
//
//  Created by haojiajia on 2022/7/9.
//

import Foundation
import Combine

struct AppListRequest {
    
    private static let baseURL = "https://itunes.apple.com/search"
    private static let limit = 10
    
    private static func appListURL(_ offset: Int) -> String {
        return baseURL + "?entity=software&term=chat&offset=\(offset)&limit=\(limit)"
    }
    
    static func publisher(_ offset: Int) -> AnyPublisher<[AppItem], AppError> {
        URLSession.shared
            .dataTaskPublisher(for: URL(string: appListURL(offset))!)
            .map{ data, _ in data }
            .decode(type: AppContent.self, decoder: JSONDecoder())
            .map{ $0.results }
            .mapError { AppError.networkingFailed($0) }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
    
}

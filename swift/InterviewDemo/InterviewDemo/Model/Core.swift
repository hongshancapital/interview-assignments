//
//  Core.swift
//  InterviewDemo
//
//  Created by Chenjun Ren on 2022/4/6.
//

import Foundation

struct AppInfo: Equatable {
    let id: String
    let name: String
    let description: String
    let iconUrl: String
    var isLiked: Bool
}

protocol AppInfoRepository {
    var numberPerFetch: Int { get }
    
    func fetchAppInfos() async -> FetchResult
    
    func refresh() async -> FetchResult
    
    func updateLikeStatus(byAppId id: String)
}

enum ServiceError: Error {
    case fetchingFailure
    
    var localizedDescription: String {
        switch self {
        case .fetchingFailure:
            return "Unable to load data!"
        }
    }
}

typealias FetchResult = Result<(result: [AppInfo], hasMore: Bool), ServiceError>

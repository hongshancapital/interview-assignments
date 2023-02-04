//

//
//  AppDataAPI.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/3.
//  
//
    

import Foundation
import Combine

enum AppDataAPI {
    static let apiClient = APIClient()
    static let baseURL = URL(string: "https://itunes.apple.com/")!
    
    // 本地缓存喜欢列表
    static var likeList: [Int] = []
}

enum AppDataAPIPath: String {
    case search = "search"
}

extension AppDataAPI {
    // 网络请求
    static func request<T: Codable>(_ path: AppDataAPIPath, _ queryItems: [URLQueryItem]) -> AnyPublisher<T, Error> {
        guard var components = URLComponents(url: baseURL.appendingPathComponent(path.rawValue), resolvingAgainstBaseURL: true) else {
            fatalError("Couldn't create URLComponents")
        }
        components.queryItems = queryItems
        let request = URLRequest(url: components.url!)
        return apiClient.request(request)
            .eraseToAnyPublisher()
    }
    
    // 修改喜欢状态
    static func requestUpdateLike(id: Int, isLike: Bool) -> AnyPublisher<Int, Error> {
        let isContains = likeList.contains(id)
        if isLike && !isContains {
            likeList.append(id)
        } else if !isLike && isContains {
            likeList.removeAll(where: { $0 == id })
        }
        return Just(200)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
}

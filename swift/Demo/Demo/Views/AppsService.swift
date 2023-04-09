//
//  AppsService.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import Foundation
import Combine

enum AppsEndpoint {
    case getApps(pageIndex: Int, pageSize: Int)
}

extension AppsEndpoint: Endpoint {
    var host: String {
        return "itunes.apple.com"
    }
    
    var path: String {
        switch self {
        case .getApps:
            return "/search"
        }
    }
    
    var queryItems: [URLQueryItem]? {
        switch self {
        case .getApps(let pageIndex, let pageSize):
            return [
                URLQueryItem(name: "entity", value: "software"),
                URLQueryItem(name: "term", value: "chat"),
                URLQueryItem(name: "offset", value: "\(pageIndex)"),
                URLQueryItem(name: "limit", value: "\(pageSize)")
            ]
        }
    }
    
    var method: RequestMethod {
        switch self {
        case .getApps:
            return .get
        }
    }
}

protocol AppServiceProtocol {
    func getApps(pageIndex: Int, pageSize: Int) async -> Result<[AppModel], Error>
    func requestApps(pageIndex: Int, pageSize: Int) -> AnyPublisher<[AppModel], Error>
}

struct AppService: HttpKit, AppServiceProtocol {
    func getApps(pageIndex: Int, pageSize: Int = 20) async -> Result<[AppModel], Error> {
        print("getApps=\(pageIndex)")
        let startDate = Date()
        
        let result = await sendRequest(
            endpoint: AppsEndpoint.getApps(pageIndex: pageIndex, pageSize: pageSize),
            responseModel: AppResponseModel.self
        )
        
        // 3秒一个接口请求，可以看清加载过程
        let interval = Date().timeIntervalSince(startDate)
        let delay = max(0, 3 - interval)
        try? await Task.sleep(nanoseconds: UInt64(delay * Double(NSEC_PER_SEC)))
        
        switch result {
        case .success(let response):
            return .success(response.results)
        case .failure(let error):
            return .failure(error)
        }
    }
    
    func requestApps(pageIndex: Int, pageSize: Int = 20) -> AnyPublisher<[AppModel], Error> {
        print("getApps=\(pageIndex)")
        return request(
            endpoint: AppsEndpoint.getApps(pageIndex: pageIndex, pageSize: pageSize),
            responseModel: AppResponseModel.self
        )
        .map(\.results)
        .eraseToAnyPublisher()
    }
}


//
//  DemoRestfulService.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation
import Combine

protocol DemoRestfulService {
    /// 首页列表
    func homePageList(limit: Int) -> AnyPublisher<[Application], Error>
    @MainActor
    func homePageList(limit: Int) async throws -> [Application]
}

struct DemoRealRestfulService: DemoRestfulService {
    let webRepository: RestfulWebRepository
    init(webRepository: RestfulWebRepository = DemoRealRestfulRepository(session: URLSession(configuration: .default), baseURL: Host.itunesHost)) {
        self.webRepository = webRepository
    }
    
    /// 首页列表
    func homePageList(limit: Int) -> AnyPublisher<[Application], Error> {
        let resultPublisher: AnyPublisher<HomePageListResult, Error> = webRepository.call(endpoint: API.homePageList(limit: limit))
            .subscribe(on: DispatchQueue.main)
           .eraseToAnyPublisher()
        return resultPublisher.map {
            $0.results
        }.eraseToAnyPublisher()
    }
    
    /// 首页列表
    @MainActor
    func homePageList(limit: Int) async throws -> [Application] {
        let result: HomePageListResult = try await webRepository.call(endpoint: API.homePageList(limit: limit))
        return result.results
    }
}

// MARK: - Endpoints
/**
 - Mock API 请求, 数据可参考 [Link](https://itunes.apple.com/search?entity=software&limit=50&term=chat)
 */
extension DemoRealRestfulService {
    enum API {
        /// 首页列表
        case homePageList(limit: Int, entity: String = "software", term: String = "chat")
    }
}

extension DemoRealRestfulService.API: APICall {
    var path: String {
        switch self {
        case .homePageList(let limit, let software, let term):
            return "/search?entity=\(software)&limit=\(limit)&term=\(term)"
        }
    }
    
    var method: HTTPMethod {
        switch self {
        case .homePageList:
            return .get
        }
    }
    
    var headers: [String: String]? {
        ["Accept": "application/json", "Content-Type": "application/json"]
    }
    
    func body() throws -> Data? {
        switch self {
        case .homePageList: break
        }
        
        return nil
    }
}

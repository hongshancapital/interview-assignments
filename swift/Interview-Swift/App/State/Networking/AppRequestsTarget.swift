//
//  HomeRequest.swift
//  App
//
//  Created by lizhao on 2022/9/20.
//

import Moya
import GCore
import Combine

enum AppRequestsTarget: BaseTargetType {
    case list(page: Int)
    case favorite(id: Int)
    case unFavorite(id: Int)
    
    var path: String {
        switch self {
        case .list(let page):
            return "/mock/6329119c10c844007734b9da/example/list/\(page)"
            
        case .favorite(let id):
            return "/mock/6329119c10c844007734b9da/example/favorite/\(id)"
            
        case .unFavorite(let id):
            return "/mock/6329119c10c844007734b9da/example/unFavorite/\(id)"
        }
    }
    
    var method: Moya.Method {
        .get
    }
    
    var task: Task {
        switch self {
        case .list, .favorite, .unFavorite:
            return Task.requestPlain
        }
    }
    
    var sampleData: Data {
        switch self {
        case .favorite(let id):
            return """
            {
              "code": 200,
              "message": "success",
              "data": {
                "id": \(id),
                "isFavorited":true
              }
            }
            """.data(using: .utf8) ?? Data()
            
        case .unFavorite(let id):
            return """
            {
              "code": 200,
              "message": "success",
              "data": {
                "id": \(id),
                "isFavorited":false
              }
            }
            """.data(using: .utf8)  ?? Data()
        default:
            return Data()
        }
    }
    
}
 
// App 列表数据
struct AppListRequest {
    let page: Int
    
    func sendRequest(dependence networkProvider: MoyaProvider<AppRequestsTarget>) -> AnyPublisher<[AppModelWrapper], AppError> {
        networkProvider.arrayRequest(.list(page: page), model: AppModel.self, key: "results")
            .map{ $0.map { AppModelWrapper($0) }}
            .mapError { AppError.networkingFailed($0)}
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}


// App 点赞
struct FavoriteAppRequest {
    let id: Int
    let isFavorited: Bool
    
    // 喜欢 or 取消喜欢
    func sendRequest(dependence networkProvider: MoyaProvider<AppRequestsTarget>) -> AnyPublisher<AppFavoriteResult, AppError> {
        networkProvider.modelRequest(isFavorited ? .unFavorite(id: id) : .favorite(id: id), model: AppFavoriteResult.self)
            .mapError {AppError.networkingFailed($0)}
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}

//
//  NetService.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import Foundation
import Combine


enum AppError: Error, Identifiable {
    var id: String { localizedDescription }
    case netFail
    case timeout
    case paramsIllegal
    case parseFail(innerError: Error?)
    case unknown(innerError: Error?)
}

extension AppError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .netFail:
            return "网络错误"
        case .timeout:
            return "网络超时"
        case .paramsIllegal:
            return "参数非法"
        case .parseFail(let err):
            return "解析失败: [\(err?.localizedDescription ?? "null")]"
        case .unknown(let err):
            return "未知错误: [\(err?.localizedDescription ?? "null")]"
        }
    }
}

struct AppRequest {
    
    func publisher(url: URL) -> AnyPublisher<[AppItem], AppError> {
        return URLSession.shared.dataTaskPublisher(for: url)
            .tryMap { (data: Data, response: URLResponse) in
                guard let response = response as? HTTPURLResponse,
                      response.statusCode == 200 else {
                    throw AppError.netFail
                }
                return data
            }
            .timeout(30, scheduler: RunLoop.main)
//            .delay(for: 2, scheduler: RunLoop.main)   // test code
            .decode(type: PaginationResponse.self, decoder: JSONDecoder())
            .mapError({
                switch $0 {
                case is DecodingError:
                    return AppError.parseFail(innerError: $0)
                case is AppError:
                    return AppError.netFail
                default:
                    return AppError.unknown(innerError: $0)
                }
            })
            .map({ $0.data })
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}

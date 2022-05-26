//
//  HSNetWork.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//

import Foundation

typealias HSNetworkResult = (data: Data, response: URLResponse)
typealias HSNetworkParams = [String: Any]

class HSNetWork {
    static let shared = HSNetWork()
    private let decoder = JSONDecoder()
    private let requestType = "GET"
    private let timeoutLimit = 15.0
    func requestAppData<T: Decodable> (from requestAPI: String, params: HSNetworkParams) async throws -> T {
        // url合法检测
        guard let url = vaildUrlCheck(from: requestAPI, params: params) else {
            throw HSNetWorkStatus.invaildURL
        }
        var request = URLRequest(url: url,
                                 cachePolicy: .returnCacheDataElseLoad,
                                 timeoutInterval: timeoutLimit )
        request.httpMethod = requestType
        
        // 判断请求状态
        guard let result = try await responseStatus(from: request) else {
            throw HSNetWorkStatus.responseError
        }
        
        // 判断请求的数据解析的状态
        do {
            return try decoder.decode(T.self, from: result.data)
        } catch {
            throw HSNetWorkStatus.decodeError
        }
    }
    
    private func responseStatus(from request: URLRequest) async throws -> HSNetworkResult? {
        return try await URLSession.shared.data(for: request)
    }
    
    private func vaildUrlCheck(from requestAPI: String, params: HSNetworkParams) -> URL? {
        guard var urlComonents = URLComponents(string: requestAPI) else {
            return nil
        }
        var queryItems = [URLQueryItem]()
        for item in params {
            queryItems.append(URLQueryItem(name: item.key, value: "\(item.value)"))
        }
        urlComonents.queryItems = queryItems
        return urlComonents.url
    }
}


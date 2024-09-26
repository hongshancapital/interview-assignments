//
//  Request.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

/// 简易请求封装
class Request: NSObject {
    
    /// GET/POST
    private var method: String = "GET"
    
    /// 请求头部参数
    private var headers: [String: String] = [:]

    /// 请求
    private var components = URLComponents()
    
    /// 请求实体
    private var body: [String: Any] = [:]
    
    /// 构造方法
    init(method: String, headers: [String: String], url: String, query: [String: String], body: [String: Any]) {
        super.init()
        self.method = method
        self.headers = headers
        components = URLComponents(string: url)!
        var queryItems: [URLQueryItem] = []
        query.forEach { key, value in
            queryItems.append(URLQueryItem(name: key, value: value))
        }
        components.queryItems = queryItems
        self.body = body
    }
    
    func dataTaskPublisher() -> URLSession.DataTaskPublisher {
        let url = components.url!
        var request = URLRequest(url: url)
        request.httpMethod = method
        self.headers.forEach { key, value in
            request.addValue(value, forHTTPHeaderField: key)
        }
        return URLSession.shared.dataTaskPublisher(for: request)
    }
    
}

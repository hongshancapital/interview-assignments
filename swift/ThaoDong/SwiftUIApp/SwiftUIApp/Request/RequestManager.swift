//
//  RequestManager.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import Foundation

public let RM = RequestManager.default

public struct RequestManager {
    
    public static let `default` = RequestManager()
    init() {
        
    }
    /// Get
    public func get(_ requestUrl: String,_ params:[String:Any] = [String:Any]()) async -> RequsetResult {
        
        let paramsArray = params.map { (key: String, value: Any) in
            return "\(key)=\(value)"
        }
        let paramsString = paramsArray.sorted(by: <).joined(separator: "&")
        let finalUrl = requestUrl + "?" + paramsString
        return await request(finalUrl, .get, params)
        
    }
    /// Post
    public func post(_ requestUrl: String,_ params:[String:Any] = [String:Any]()) async -> RequsetResult {
        return await request(requestUrl, .post, params)
        
    }
    
    public func request(_ requestUrl: String, _ method: RequsetMethod = .get, _ params:[String:Any]) async -> RequsetResult{
        var result = RequsetResult()
        
        guard let url = URL(string: requestUrl) else {
            result.failMessage = "请求地址错误"
            return result
        }
        do {
            let requsetMothod = method.rawValue
            var requset = URLRequest(url: url)
            requset.httpMethod = requsetMothod
            if method == .post {
                let httpBody = try JSONSerialization.data(withJSONObject: params, options: [])
                requset.httpBody = httpBody
            }
            let (data, response) = try await URLSession.shared.data(for: requset)
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode  else { throw NSError(domain: "swiftui.app.demo", code: -1)}
            switch statusCode {
            case 200:
                result.state = .success
                result.failMessage = ""
                result.data = data
            case 500:
                result.state = .fail
                result.failMessage = "服务器内部错误"
            case 404:
                result.state = .fail
                result.failMessage = "找不到API"
            default:
                result.state = .fail
            }
        }
        catch {
        }
        
        return result
    }
    
}

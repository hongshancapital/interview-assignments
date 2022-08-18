//
//  NetWorkManager.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import Foundation
import Combine

class NetWorkManager {
    
    static let shared = NetWorkManager()
    private init() {}
    
    enum HttpMethodType: String {
        case GET, POST
    }
    
    /// 接口请求
    /// - Parameters:
    ///   - url: 请求链接
    ///   - type: 返回数据的数据类型
    ///   - method: 接口请求类型，默认GET
    ///   - params: 参数（POST请求时使用）
    /// - Returns: 接口结果
    func request<T: Decodable>(url: String, type: T.Type, method: HttpMethodType = .GET, params: Data? = nil) async -> ResultModel<T> {
        // 对url进行编码，防止url中包含特殊字符或汉字导致URL转换失败
        let URL = URL(string: url.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)

        guard URL != nil else {
            return ResultModel.failure(msg: "接口请求失败, 无效接口")
        }
        
        var request = URLRequest(url: URL!)
        request.httpMethod = method.rawValue
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        if method == .POST {
            guard let encoded = try? JSONEncoder().encode(params) else {
                return ResultModel.failure(msg: "接口请求失败, 参数无效")
            }
            request.httpBody = encoded
        }

        do {
            let (data, response) = try await URLSession.shared.data(for: request)
            
            guard (response as? HTTPURLResponse)?.statusCode == 200 else {
                return ResultModel.failure(msg: "接口请求失败, 接口异常")
            }
            
            guard let result = try? JSONDecoder().decode(T.self, from: data) else {
                return ResultModel.failure(msg: "接口请求失败, 数据解析失败")
            }
            
            return ResultModel.success(result, msg: "接口请求成功")
        } catch(let error) {
            return ResultModel.failure(msg: error.localizedDescription)
        }
    }
}

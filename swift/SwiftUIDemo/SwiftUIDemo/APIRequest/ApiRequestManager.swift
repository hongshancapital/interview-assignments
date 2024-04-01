//
//  ApiRequestManager.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/20.
//

import Foundation

/// 网络请求方式
enum ApiRequestMethod {
    case GET
    case POST
}


class ApiManager : NSObject {
    
    
    static let shared : ApiManager = {
        let instance = ApiManager()
        return instance;
    }()
    
    static var baseUrl : String {
        return "https://itunes.apple.com/search"
    }
    
    /// 加载数据
    /// limit  : 单次获取数据的最大长度，默认15
    /// offset : 每次获取数据的索引，默认0，从第一个开始获取
    func asyncLoadSearchDatas(limit : Int = 15 , offset : Int = 0 , onCompleted : @escaping ( (_ res : ListDataRes) -> Void ))  {
        
        /// 初始化Session
        let session = URLSession.shared;
        /// 创建URL
        if let url = URL(string: ApiManager.baseUrl+"?entity=software&limit=\(limit)&term=chat&offset=\(offset)") {
            /// 创建URLRequest
            let urlRequest = createURLRequest(url)
            /// 开始进行异步网络请求
            session.dataTask(with: urlRequest) { data, response, error in
                var _resultRes : ListDataRes
                if (error == nil && data != nil) {
                    /// success
                    let _res = try? JSONDecoder().decode(ListDataRes.self, from: data!);
                    _resultRes = _res ?? ListDataRes(success: false, msg: "JSON转ListDataRes失败")
                    _resultRes.success = true
                } else {
                    /// failed
                    _resultRes = ListDataRes(success: false, msg: "接口请求失败：\(error?.localizedDescription ?? "返回数据为空")")
                }
                DispatchQueue.main.async {
                    onCompleted(_resultRes)
                }
            }.resume()
        } else {
            /// failed
            var _resultRes : ListDataRes
            _resultRes = ListDataRes(success: false, msg: "接口请求失败:Request创建失败")
            onCompleted(_resultRes)
        }
    }
    
    /// 创建UrlRequest
    private func createURLRequest(_ url : URL , method : ApiRequestMethod = .GET) -> URLRequest {
        var request = URLRequest(url: url);
        var methodString = "GET"
        if (method == .POST) {
            methodString = "POST"
        }
        request.httpMethod = methodString;
        requestHeaders().forEach { (key: String, value: String) in
            request.setValue(value, forHTTPHeaderField: key)
        }
        return request
    }
    
    /// request 请求头
    private func requestHeaders() -> Dictionary<String,String> {
        return [
            "UTF-8" : "Charset",
            "application/json" : "Content-Type",
        ];
    }
    
}

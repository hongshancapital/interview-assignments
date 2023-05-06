//
//  IndonesAPi.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya
import Alamofire

enum IndonesAPi {
    case appList(pageSize: Int, index: Int)
}

extension IndonesAPi: BaseTargetType {
    var baseURL: URL {
        switch Network.config.environment {
        case .appStore:
            return URL(string: "https://api.xxxxxx.com")!
        case .preview:
            return URL(string: "https://api.xxxxxx.com")!
        case .develop:
            return URL(string: "https://api.xxxxxx.com")!
        }
    }

    var route: Route {
        switch self {
        case .appList:
            return .post("/api/xxxxxx/xxxxxx")
        }
    }

    var task: Task {
        switch self {
        case let .appList(pageSize, index):
            return requestParameters(params: ["pageSize": pageSize, "index": index])
        }
    }
    
    private func requestParameters(params: [String: Any]) -> Task {
        #warning("可能有数据加密逻辑")
//        let encrypted = ParamsEncrypt.encrypt(params: params)
        let encrypted = ""
        return .requestParameters(parameters: ["data": encrypted], encoding: URLEncoding.httpBody)
    }
    
    /// mock 数据
    private static var rawJson: [String: Any]?
    private func queryRawJson() -> [String: Any] {
        if let rawJson = IndonesAPi.rawJson { return rawJson }
        let url = Bundle.main.url(forResource: "getSocialAppList", withExtension: "json")
        let data = (try? Data(contentsOf: url!)) ?? Data()
        let json = (try? JSONSerialization.jsonObject(with: data, options: .allowFragments) as? [String: Any]) ?? [String: Any]()
        IndonesAPi.rawJson = json
        return json
    }
    var sampleData: Data {
        switch self {
        case let .appList(pageSize, index):
            var json = queryRawJson()
            
            var datas = json["results"] as? [Any] ?? []
            let location = (index-1) * pageSize
            let length = (datas.count >= index*pageSize) ? pageSize : (datas.count - (index-1)*pageSize)
            if length <= 0 {
                datas = []
            } else {
                datas = (datas as NSArray).subarray(with: .init(location: location, length: length))
            }
            json["results"] = datas
            
            var res: [String : Any] = [:]
            res["code"] = 200
            res["message"] = "success"
            res["data"] = json
            
            let jsonData = (try? JSONSerialization.data(withJSONObject: res, options: .prettyPrinted)) ?? Data()
            return jsonData
        }
    }
}

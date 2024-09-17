//
//  NetworkReponse.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import Foundation


extension Data {
    func map<D: Decodable>(_ type: D.Type, using decoder: JSONDecoder = JSONDecoder()) throws ->D {
        try decoder.decode(type, from: self)
    }    
}

extension Encodable {
    func mapJson(_ encoder: JSONEncoder = JSONEncoder()) -> Any? {
        guard let jsonData = try? encoder.encode(self) else { return  nil }
        let json = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        return json
    }
}

//MARK: 网络请求响应
struct NetworkReponse<Data>: Codable where Data: Codable {
    let code: Int
    let message: String
    let data: Data?
    
    var responseObj: Any {
        if let json = mapJson() {
            return json
        }
        return [
            "code": 0,
            "message" : "解析失败"
        ]
    }
}

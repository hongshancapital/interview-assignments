//
//  RequestResponse.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/21.
//

import Foundation

extension Data {
    func map<D: Decodable>(_ type: D.Type, using decoder: JSONDecoder = JSONDecoder()) throws ->D {
        try decoder.decode(type, from: self)
    }
}

extension Encodable {
    func jsonEncode(_ encoder: JSONEncoder = JSONEncoder()) -> Any? {
        guard let jsonData = try? encoder.encode(self) else { return  nil }
        let json = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        return json
    }
}

struct RequestResponse<Data>: Codable where Data: Codable {
    let code: Int
    let message: String
    let data: Data?
    
    var responseObj: Any {
        if let json = jsonEncode() {
            return json
        }
        return [
            "code": 0,
            "message" : "解析失败"
        ]
    }
}

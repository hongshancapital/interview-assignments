//
//  configs.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation

typealias Params = [String: Any]

extension Params {
    
    var pageNum: Int {
        get {
            return self["pageNum"] as? Int ?? 1
        }
        set {
            self["pageNum"] = newValue
        }
    }
    
    var pageSize: Int {
        get {
            return self["pageSize"] as? Int ?? 20
        }
        set {
            self["pageSize"] = newValue
        }
    }
}

enum NetworkCode: Int {
    case success = 0
    case noNetwork = 10001
    case serverError = 10002
    case paramsError = 10003
}

class NetworkResponse: ObservableObject {
    
    let code: NetworkCode
    let message: String?
    let data: [String: Any]?
    
    init(code: NetworkCode, message: String?, data: [String: Any]?) {
        self.code = code
        self.message = message
        self.data = data
    }
}

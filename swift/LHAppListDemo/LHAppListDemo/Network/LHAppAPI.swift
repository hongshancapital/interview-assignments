//
//  LHAPISearchApp.swift
//  LHAppListDemo
//
//  Created by 刘志华 on 2022/5/14.
//
import Foundation
import Moya

enum LHAppAPI {
    case searchApp(entity:String,limit:Int,term:String,offset:Int)
}

extension LHAppAPI : TargetType {
    var baseURL: URL {
        return URL(string: "https://itunes.apple.com")!
    }
    
    var path: String {
        switch self {
        case .searchApp:
            return "search"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .searchApp:
            return .get
        }
    }
    
    var task: Task {
        switch self {
        case .searchApp(let entity,let limit,let term,let offset):
            return .requestParameters(parameters: ["entity":entity,"limit":limit,"term":term,"offset":offset], encoding: URLEncoding.default)
        }
    }
    
    var headers: [String : String]? {
        return ["Content-Type":"application/x-www-form-urlencoded"]
    }
    
    
}

//
//  AppDataModel.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import Foundation

/// AppError
public enum AppError: Error {
    case network(String)
    case error(Error)
    
    var string: String {
        switch self {
        case let .network(value):
            return value
        case let .error(value):
            return "\(value)"
        }
    }
}

/// AppData
public struct AppData: Identifiable, Equatable {
    /// 标识符
    public var id: String = ""
    /// 图片地址
    public var imageUrl: String = ""
    /// 标题
    public var title: String = ""
    /// 描述
    public var description: String = ""
    /// 是否点赞
    public var like: Bool = false
    
    init(data: Dictionary<String, Any>) {
        self.id = data["bundleId"] as? String ?? ""
        self.imageUrl = data["artworkUrl100"] as? String ?? ""
        self.title = data["trackName"] as? String ?? ""
        self.description = data["description"] as? String ?? ""
    }

}

extension AppData {
        
    static func toObj(data: [String : Any]) -> AppData {
        .init(data: data)
    }
    
    static func toArray(data: [[String : Any]]) -> [AppData] {
        data.map({ AppData(data: $0) })
    }

}


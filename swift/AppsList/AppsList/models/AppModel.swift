//
//  AppModel.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation

class AppModel: ObservableObject, Identifiable {
    /// 标识符
    var id: String?
    /// 图片地址
    var imageUrl: String?
    /// 标题
    var title: String?
    /// 描述
    var description: String?
    /// 是否喜欢
    @Published var like = false
}

extension AppModel {
    
    convenience init(data: Dictionary<String, Any>) {
        self.init()
        self.id = data["bundleId"] as? String
        self.imageUrl = data["artworkUrl100"] as? String
        self.title = data["trackName"] as? String
        self.description = data["description"] as? String
    }
    
}

extension AppModel: JsonParserProtocol {
    
    typealias T = AppModel
    
    static func toObj(data: [String : Any]) -> AppModel? {
        return AppModel.init(data: data)
    }
    
    static func toArray(data: [[String : Any]]) -> [AppModel]? {
        return data.toApps()
    }
}

private extension Array where Element == Dictionary<String, Any> {
    
    func toApps() -> [AppModel] {
        return map { element in
            return AppModel.init(data: element)
        }
    }
    
}

//
//  MockAppList.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import Foundation
import MJModule
import UIKit

struct MockAppList {
    static var shared: Self {
        return .init()
    }
    
    let appList: [AppInfo]
    
    fileprivate init() {
        if let fileData = NSDataAsset(name: "app_list") {
            do {
                let originAppList = try JsonDecodeWrapper.decoder.decode(OriginAppList.self, from: fileData.data)
                  // 解析数据
                appList = originAppList.results
                return
            } catch {
                LogError(error)
            }
        }
        appList = []
    }
}

struct OriginAppList : Codable {
    let resultCount: Int
    let results: [AppInfo]
}

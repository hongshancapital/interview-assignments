//
//  Mock.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import UIKit

class Mock {
    
    static func load() -> Data? {
        let url = Bundle.main.url(forResource: "Links", withExtension: ".json")
        var data: Data?
        do {
            if let url = url {
                data = try Data(contentsOf: url)
            }
        } catch  {
            debugPrint("获取数据文件失败: \(error.localizedDescription)  url: \(url?.absoluteString ?? "")")
        }
        return data
    }
}

var mockData: Data? = Mock.load()



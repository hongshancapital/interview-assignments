//
//  DataManager.swift
//  Demo
//
//  Created by hbc on 2022/2/19.
//  Copyright © 2022 hbc. All rights reserved.
//

import Foundation

struct DataManager {
    let filePath: String
    let pageCount: Int
    
    init(filePath:String, pageCount:Int) {
        self.filePath = filePath
        self.pageCount = pageCount
    }
    
    mutating func reloadAppList() -> [AppInfo]? {
        return loadAppList(location: 0, pageCount: self.pageCount)
    }
    
    mutating func loadMoreAppList(location:Int) -> [AppInfo]? {
        return loadAppList(location: location, pageCount: self.pageCount)
    }
    
    mutating func loadAppList(location:Int , pageCount:Int = 10) -> [AppInfo]? {
        var rst:[AppInfo]? = nil
        guard self.filePath.count > 0 else {
            print("文件路径有误")
            return nil
        }
        
        if let filepath = Bundle.main.path(forResource: self.filePath, ofType: nil) {
            do {
                let decoder = JSONDecoder()
                let contents = try String(contentsOfFile: filepath)
                if let data = contents.data(using: String.Encoding.utf8) {
                    let resourceData = try? decoder.decode(ResourceData.self, from: data)
                    if let arr = resourceData?.results {
                        if location < arr.count {
                            let end = location+pageCount < arr.count ? location+pageCount : arr.count
                            rst = Array(arr[location ..< end])
                        }
                    }
                }
            } catch {
                print("内容无法加载")
            }
        } else {
            print("文件\(self.filePath)未找到")
        }
        
        return rst
    }
    
}


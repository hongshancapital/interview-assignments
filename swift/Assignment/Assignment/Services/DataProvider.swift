//
//  Enumeration.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import Foundation

struct DataProvider {
    static func fetchData(pageNum: Int, pageSize: Int = 10) -> [CellModel] {
        let result = fetchAllData()
        let total = pageNum * pageSize
        if pageNum < 1 {
            NotificationCenter.default.post(name: NSNotification.Name("HasMoreData"), object: false)
            return []
        } else {
            NotificationCenter.default.post(name: NSNotification.Name("HasMoreData"), object: !(total >= result.count))
            return Array(result.prefix(total))
        }
    }
    
    static func fetchAllData() -> [CellModel] {
        let data = loadData()
        let result = Result(from: data).result
        return result
    }
    
    private static func loadData() -> Data {
        let data: Data
        
        let fileName = "data.json"
        
        guard let filePath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first?.appending("/\(fileName)") else {
            fatalError("Could not find \(fileName) in path.")
        }
        
        if !FileManager.default.fileExists(atPath: filePath) {
            fatalError("Could not find \(fileName) in path.")
        }
        
        do {
            data = try Data(contentsOf: URL(fileURLWithPath: filePath))
        } catch {
            fatalError("Could not load data from \(fileName) for \(error)")
        }
        
        return data
    }
    
    // Copy data.json from bundle to sandbox
    static func copyDataFile() -> Void {
        guard let file = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first else {
            fatalError("Could not get path of Document folder")
        }
        
        let filePath = file.appending("/data.json")
        
        if !FileManager.default.fileExists(atPath: filePath) {
            let bundlePath = Bundle.main.path(forResource: "data", ofType: "json") ?? ""
            try? FileManager.default.copyItem(atPath: bundlePath, toPath: filePath)
        }
    }
    
    static func changeFavorToFile(model: CellModel, success: @escaping () -> Void, failure: @escaping (_ error: Error) -> Void) -> Void {
        var modelList = fetchAllData()
        
        for (idx, item) in modelList.enumerated() {
            if item.id == model.id {
                modelList[idx].isFavorite = !(modelList[idx].isFavorite)
            }
        }
        
        let result = Result(modelList: modelList)
        if let data = result.data() {
            guard let filePath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first?.appending("/data.json") else {
                fatalError("Could not find data.json in path.")
            }
            
            do {
                try FileManager.default.removeItem(atPath: filePath)
                FileManager.default.createFile(atPath: filePath, contents: data)
                success()
            } catch {
                failure(error)
            }
        }
    }
}

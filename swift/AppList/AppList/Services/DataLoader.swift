//
//  DataLoader.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

struct DataLoader {
    func getData(of page: Page) -> (total: Int, list: [ListItem]) {
        let listCount = getAllJSON().total
        let jsonList = getJSON(of: page).list
        if let listData = jsonList.data {
            let tuple: (Int, [ListItem]) = (listCount, Decoder().decode(data: listData))
            return tuple
        }
        return (0, [])
    }
    
    func getJSON(of page: Page) -> (total: Int, list:[[String: Any]]) {
        let listCount = getAllJSON().total
        var somePage = page
        somePage.total = listCount
        if let jsonList = (loadFileData("data.json").jsonObject as? [[String: Any]]), let range = somePage.range {
            return (listCount, Array(jsonList[range]))
        }
        return (0, [])
    }
    
    func getAllJSON() -> (total: Int, json:[[String: Any]]) {
        if let jsonList = (loadFileData("data.json").jsonObject as? [[String: Any]]) {
            return (jsonList.count, jsonList)
        }
        return (0, [])
    }
    
    func loadFileData(_ filename: String) -> Data {
        let data: Data
        guard let file = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first?.appending("/\(filename)") else {
            fatalError("Couldn't find \(filename) in document dir.")
        }
        
        if !FileManager.default.fileExists(atPath: file) {
            fatalError("Couldn't find \(filename) in document dir.")
        }
        
        let fileUrl = URL(fileURLWithPath: file)
        
        do {
            data = try Data(contentsOf: fileUrl)
        } catch {
            fatalError("Couldn't load \(filename) from document dir:\n\(error)")
        }
        return data
    }
}

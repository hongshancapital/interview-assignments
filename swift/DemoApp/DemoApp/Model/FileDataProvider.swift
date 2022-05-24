//
//  FileDataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/20.
//

import Foundation

class FileDataProvider: DataProvider {
    var appList = [AppModel]()
    let fileName = "data.json"
    init() {
        guard let url = Bundle.main.url(forResource: fileName, withExtension: nil)
        else {
            fatalError("未找到数据文件: \(fileName)")
        }
        guard let data = try? Data.init(contentsOf: url)
        else {
            fatalError("无法加载数据文件: \(fileName)")
        }
        guard let json = try? JSONSerialization.jsonObject(with: data, options: [])
        else {
            fatalError("未能正确解析数据文件: \(fileName)")
        }
        
        if let rootJson = json as? [String: Any] {
            guard let jsonArray = rootJson["results"] as? [[String: Any]]
            else {
                fatalError("未能正确解析results: \(fileName)")
            }
            for aJson in jsonArray {
                let anApp = AppModel(data: aJson)
                appList.append(anApp)
            }
        }
    }
    
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?) -> Void) {
        var startIndex = 0
        if let startId = last?.id {
            for i in 0...appList.count {
                let anApp = appList[i]
                if anApp.id == startId {
                    startIndex = i+1
                    break
                }
            }
        }
        DispatchQueue.global().asyncAfter(deadline: .now() + 2) {[self] in
            if (startIndex+count) <= appList.count  {
                let list = [AppModel].init(appList[startIndex...startIndex+count-1])
                completion(list, nil)
            } else {
                completion([], NSError.init(domain: "", code: 999, userInfo: nil))
            }
        }
    }
}

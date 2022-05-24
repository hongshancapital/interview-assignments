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
        if let list = AppModel.parseList(from: data) {
            appList = list
        }
    }
    
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?) -> Void) {
        let subList = subList(within: appList, from: last, count: count)
        DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
            if let result = subList {
                completion(result, nil)
            } else {
                completion(nil, NSError.init(domain: "", code: 999, userInfo: nil))
            }
        }
    }
}

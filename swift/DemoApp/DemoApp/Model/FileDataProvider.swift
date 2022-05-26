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
        
        let apiRsp = try? JSONDecoder().decode(ApiResponse.self, from: data)
        if let list = apiRsp?.results {
            appList = list
        }
    }
    
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?) -> Void) {
        let subList = subList(within: appList, from: last, count: count)
        if let result = subList {
            delay(appList: result, error: nil, completion: completion)
        } else {
            delay(appList: nil, error: DataProviderError.noResultsList, completion: completion)
        }
    }
}

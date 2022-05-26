//
//  DataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation

enum DataProviderError: Error {
    case requestFailed
    case inValidRawData
    case noResultsList
}

protocol DataProvider {
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?)->Void);
    func subList(within appList:[AppModel], from last: AppModel?, count: Int) -> [AppModel]?
}

extension DataProvider {
    func subList(within appList:[AppModel], from last: AppModel?, count: Int) -> [AppModel]? {
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
        if startIndex < appList.count {
            var endIndex = appList.count - 1
            if (startIndex+count) <= appList.count {
                endIndex = startIndex + count - 1
            }
            let list = [AppModel].init(appList[startIndex...endIndex])
            return list
        }
        return nil
    }
    
    func parseAppList(from data: Data) throws -> [AppModel] {
        let apiRsp: ApiResponse
        do {
            try apiRsp = JSONDecoder().decode(ApiResponse.self, from: data)
        } catch {
            throw DataProviderError.inValidRawData
        }
        return apiRsp.results
    }
    
    func delay(appList: [AppModel]?, error: Error?, completion: @escaping ([AppModel]?, Error?) -> Void) {
        DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
            completion(appList, error)
        }
    }
}

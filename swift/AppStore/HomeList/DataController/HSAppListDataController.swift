//
//  HSAppListDataController.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation

class HSAppListDataController: HSListDataController<HSAppInfo> {
    var limitCount: Int = 50

    override func requestEndPoint() -> APIService.Endpoint {
        return .search
    }

    override func requestParma() -> [String: String] {
        let params = ["entity": "software",
                      "limit": NSNumber(value: limitCount).stringValue,
                      "term": "chat"]
        return params
    }

    override func parseDataItems(from responseData: [String: Any], isRefresh: Bool) -> [HSAppInfo] {
        guard let appInfos = responseData["results"] as? [Dictionary<String, Any>],
              let appModels = NSArray.yy_modelArray(with: HSAppInfo.self, json: appInfos) as? [HSAppInfo] else {
            print("json decode error")
            return [HSAppInfo]()
        }

        // maybe
        hasMore = (appModels.count == limitCount)
        #if DEBUG
        hasMore = !(self.items.count > 100)
        #endif
        return appModels
    }
}

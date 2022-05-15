//
//  HSAppListTestData.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation

let appInfoData = loadTestData()

func loadTestData() -> [HSAppInfo] {
    if let path = Bundle.main.path(forResource: "appList.json", ofType: nil),
       let json = try? JSONSerialization.jsonObject(with: NSData(contentsOfFile: path) as Data) as? [String: Any],
       let appInfoDictList = json["results"] as? [AnyObject],
       let appInfoList = NSArray.yy_modelArray(with: HSAppInfo.self, json: appInfoDictList) as? [HSAppInfo] {
        return appInfoList
    }
    return [HSAppInfo]()
}

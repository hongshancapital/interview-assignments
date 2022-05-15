//
//  HSListDataControllerProtocol.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation

protocol HSListDataControllerProtocol {
    associatedtype Item: AnyObject

    func requestEndPoint() -> APIService.Endpoint
    func requestParma() -> [String: String]
    func parseDataItems(from responseData: [String: Any], isRefresh: Bool) -> [Item]
}

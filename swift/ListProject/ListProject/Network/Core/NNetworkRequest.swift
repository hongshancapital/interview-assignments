//
//  NNetworkRequest.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation

class NNetworkRequest {
    var header: [String: String] = [:]
    var host: String = ""
    var timeout: TimeInterval = 15
    var methodName: String = ""
    var params: [String: Any] = [:]
    var requestMethod: RequestMethod = .GET

    var files: [String: NFileData] = [:]

    init() {
        header["Content-Type"] = "application/x-www-form-urlencoded"
    }
}

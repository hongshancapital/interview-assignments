//
//  NJSONNetworkRequest.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation

class NJSONNetworkRequest: NNetworkRequest {
    override init() {
        super.init()
        header["Content-Type"] = "application/json;charset=UTF-8"
    }
}

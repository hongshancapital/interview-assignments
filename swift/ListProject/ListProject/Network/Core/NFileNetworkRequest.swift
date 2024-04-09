//
//  NFileNetworkRequest.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation

class NFileNetworkRequest: NNetworkRequest {
    override init() {
        super.init()
        header = [:]
        self.timeout = 60
    }
}

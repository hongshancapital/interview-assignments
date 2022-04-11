//
//  Network.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import Foundation
import Combine

typealias NetworkResult = (data: Data, response: URLResponse)

class Network {
    static let shared = Network()
    private let decoder = JSONDecoder()
    
}

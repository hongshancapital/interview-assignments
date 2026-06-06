//
//  AppsNetworkProtocol.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation

protocol AppNetworkProtocol {
    associatedtype T

    ///
    /// Get an array of T ty pe apps data at (page) of (count) number
    /// (page) - expecting data from this page
    /// (count) - maximum number of items to return
    ///
    func getApps(_ page: Int, _ count: Int) async -> [T]
}

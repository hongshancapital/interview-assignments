//
//  AppRawItems.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation

///
/// Struct to map JSON response
///
struct AppRawItems: Codable {
    var resultCount: Int
    var results: [AppRawItem]
}

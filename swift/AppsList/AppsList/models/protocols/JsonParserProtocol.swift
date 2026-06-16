//
//  JsonParserProtocol.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation

protocol JsonParserProtocol {
    
    associatedtype T
    
    static func toObj(data: [String: Any]) -> T?
    
    static func toArray(data: [[String: Any]]) -> [T]?
    
}

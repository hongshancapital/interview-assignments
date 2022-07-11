//
//  Log.swift
//  DJAppList
//
//  Created by haojiajia on 2022/7/7.
//

import Foundation

func Log<T>(_ message : T) {
    #if DEBUG
        print("\(message)")
    #endif
}

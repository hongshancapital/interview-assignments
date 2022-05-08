//
//  PageEndPoint.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation

public enum PageEndPoint {
    
    case pageNo(pageIndex: Int, pageSize: Int)
    func path() -> String {
        switch self {
        case let .pageNo(pageIndex, pageSize):
            return "&limit=" + String(pageIndex * pageSize)
        }
    }
    
    func pageIndex() -> Int {
        switch self {
        case let .pageNo(pageIndex, _):
            return pageIndex
        }
    }
}

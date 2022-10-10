//
//  ItemResponse.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

struct ItemResponse<T: Decodable>: Decodable {
    let items: [T]
}

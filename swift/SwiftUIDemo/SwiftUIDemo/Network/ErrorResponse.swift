//
//  ErrorResponse.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

struct ErrorResponse: Decodable, Error {
    let errorCode: Int
    let message: String
}


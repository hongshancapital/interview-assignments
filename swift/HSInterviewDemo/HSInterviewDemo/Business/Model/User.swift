//
//  User.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService
import BetterCodable

struct User: APIDefaultJSONParsable, Identifiable, Equatable {
    @DefaultEmptyString
    var artworkUrl60: String = ""
    
    @DefaultEmptyString
    var description: String = ""

    @DefaultEmptyString
    var trackName: String = ""

    @DefaultEmptyString
    var imgUrl: String = ""
    
    @DefaultIntZero
    var id: Int = 0
}

//
//  FLUser.swift
//  demo
//
//  Created by 张帅 on 2023/4/8.
//

import Foundation
import SwiftUI

struct FLUser: Hashable, Decodable {
    var artistId: Int
    var description: String
    var trackName: String
    var artworkUrl100: String
    var islike: Bool? = false
}





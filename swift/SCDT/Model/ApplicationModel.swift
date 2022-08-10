//
//  ApplicationModel.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import Foundation

struct ApplicationModel: Decodable, Hashable {
    var trackId: Int
    var trackCensoredName: String
    var description: String
    var artworkUrl60: String
}

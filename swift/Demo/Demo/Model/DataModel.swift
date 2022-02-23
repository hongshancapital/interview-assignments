//
//  DataModel.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import Foundation
import SwiftUI

struct KKData: Codable {
    let resultCount: Int
    let results: [KKModel]
}


struct KKModel: Codable, Identifiable {
    var id: String { return trackCensoredName }
    let trackCensoredName: String
    let description: String
    let artworkUrl60: String
    var like: Bool?
}

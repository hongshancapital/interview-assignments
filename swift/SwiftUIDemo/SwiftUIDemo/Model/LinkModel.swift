//
//  LinkModel.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import Foundation
import UIKit

struct LinkModel: Identifiable, Codable {
    var id: UUID = UUID()
    var title: String
    var body: String
    var favorite: Bool = false
    var imageURLString: String
    
    private enum CodingKeys: String, CodingKey {
        case title = "trackName"
        case body = "releaseNotes"
        case imageURLString = "artworkUrl60"
    }
}

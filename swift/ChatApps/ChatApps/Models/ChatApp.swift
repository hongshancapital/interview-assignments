//
//  ChatApp.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import Foundation

struct ChatApp: Codable, Identifiable {
  var trackId: UInt
  var trackName: String
  var description: String
  var artworkUrl100: String
  
  var isFavorite = false
  
  var id: UInt {
    return trackId
  }
  
  private enum CodingKeys: String, CodingKey {
    case trackId, trackName, description, artworkUrl100
  }
}

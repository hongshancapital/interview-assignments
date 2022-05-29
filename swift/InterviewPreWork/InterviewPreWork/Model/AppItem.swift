//
//  AppItem.swift
//  InterviewPreWork
//
//  Created by jeffy on 2022/5/29.
//

import Foundation


struct AppItem: Codable, Identifiable {
    
    var id: Int
    var trackName: String
    var description: String
    var artworkUrl60: URL
    
   private enum CodingKeys: String, CodingKey {
       case id = "trackId"
       case trackName
       case description
       case artworkUrl60
    }
}


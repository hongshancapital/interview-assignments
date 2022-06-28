//
//  AppItemInfo.swift
//  Demo
//
//  Created by milomiao on 2022/6/21.
//

import Foundation
import Combine

class AppItemInfo: ObservableObject, Decodable, Identifiable {
    // MARK: Codable
    
    private enum CodingKeys: String, CodingKey {
        case artworkUrl60
        case trackCensoredName
        case description
        case bundleId
    }
    
    var title: String?
    var subtitle: String?
    var iconURL: String?
    var id: String?
    
    @Published var isLike: Bool = false
        
    var cancellable: Cancellable?
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        subtitle = try? values.decode(String.self, forKey: .description)
        title = try? values.decode(String.self, forKey: .trackCensoredName)
        iconURL = try? values.decodeIfPresent(String.self, forKey: .artworkUrl60)
        id = try? values.decode(String.self, forKey: .bundleId)
    }
    
    required init() {
    }
        
    var likeIcon : String {
        if isLike {
            return "heart.fill"
        } else {
            return "heart"
        }
    }
    
    func toggleLike() {
        isLike = !isLike
    }
}

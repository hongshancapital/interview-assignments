//
//  CellModel.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//
import Foundation

struct Result:Codable {
    
    var result: [CellModel]
    
    init(from data: Data) {
        result = (try? JSONDecoder().decode(Result.self, from: data))?.result ?? []
    }
    
    init(modelList: [CellModel]) {
        result = modelList
    }
    
    func data() -> Data? {
        let encoder = JSONEncoder()
        
        do {
            let jsonData = try encoder.encode(self)
            let jsonStr = String(bytes: jsonData, encoding: .utf8)
            return jsonStr?.data(using: .utf8)
        } catch {
            fatalError("Failed to convert model to data for \(error)")
        }
    }
}

struct CellModel: Codable, Identifiable {
    
    var id: Int = 0
    var icon: String! = ""
    var name: String! = ""
    var description: String! = ""
    
    var isFavorite: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case icon = "artworkUrl100"
        case name = "trackName"
        case description
        case isFavorite
    }
    
    init() {}
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.id = try container.decode(Int.self, forKey: .id)
        self.icon = try container.decodeIfPresent(String.self, forKey: .icon)
        self.name = try container.decodeIfPresent(String.self, forKey: .name)
        self.description = try container.decodeIfPresent(String.self, forKey: .description)
        
        do {
            self.isFavorite = try container.decode(Bool.self, forKey: .isFavorite)
        } catch {
            self.isFavorite = false
        }
    }
}

struct TestModel: Identifiable {
    var id = UUID()
    var isFavorite = false
}

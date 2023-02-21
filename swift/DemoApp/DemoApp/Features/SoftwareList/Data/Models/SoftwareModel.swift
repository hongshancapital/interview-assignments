//
//  SoftwareModel.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/20.
//

import Foundation

extension Software {
    static func fromJson(data: Data) throws -> Software {
        do {
            let software = try JSONDecoder().decode(self, from: data)
            return software
        } catch {
            throw error
        }
    }
    
    func toJson() throws -> Data {
        do {
            let json = try JSONEncoder().encode(self)
            return json
        } catch {
            throw error
        }
    }
}

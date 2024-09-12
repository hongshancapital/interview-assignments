//
// Homework
// Mock.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import Foundation

struct Mock {
    static let mockData: [[String: Any]] = {
        guard let fileURL = try? fileURL(),
              let file = try? FileHandle(forReadingFrom: fileURL) else {
            return []
        }
        let data = file.availableData
        guard let jsonObject = try? JSONSerialization.jsonObject(with: data),
              let json = jsonObject as? [String: Any] else {
            return []
        }
        return json["results"] as! [[String: Any]]
    }()
    
    private static func fileURL() throws -> URL {
        guard let path = Bundle.main.path(forResource: "app-list", ofType: "json") else {
            throw AppError.fileError
        }
        return URL(fileURLWithPath: path)
    }
}

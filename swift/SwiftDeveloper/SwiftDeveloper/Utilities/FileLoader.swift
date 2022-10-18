//
//  FileLoader.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/18.
//

import Foundation

class FileLoader {
    static func loadAppsDataFromFilename(_ filename: String) -> AppsModel? {
        guard let file = Bundle.main.path(forResource: filename, ofType: "json")
        else {
            fatalError("Unable to locate file \"\(filename)\" in main bundle.")
        }

        do {
            let data = try String(contentsOfFile: file).data(using: .utf8)

            if let data = data {
                return try JSONDecoder().decode(AppsModel.self, from: data)
            } else {
                return nil
            }
        } catch {
            fatalError("Unable to load apps data with error: \(error)")
        }
    }
}

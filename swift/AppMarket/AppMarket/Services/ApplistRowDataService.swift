//
//  ApplistRowDataService.swift
//  AppMarket
//
//  Created by xcz on 2022/8/28.
//

import Foundation
import SwiftUI

class ApplistRowDataService {
    
    func downloadImage(url: URL) async throws -> UIImage? {

        do {
            let (data, response) = try await URLSession.shared.data(from: url)
            guard let image = UIImage(data: data),
                  let response = response as? HTTPURLResponse,
                  response.statusCode >= 200 && response.statusCode < 300
            else {
                print("Response data parsing failed.\(data)  \n  \(response)")
                return nil
            }
            return image
        } catch {
            throw error
        }
        
    }

}




//
//  CachedAsyncImageViewModel.swift
//  AppMarket
//
//  Created by xcz on 2022/9/7.
//

import Foundation
import SwiftUI

class CachedAsyncImageViewModel: ObservableObject {
    
    @Published var image: UIImage? = nil
    @Published var isLoading = false

    private let url: URL
    
    init(url: URL) {
        self.url = url
        Task {
            await fetchImage()
        }
    }
    
    
    @MainActor
    func fetchImage() async {

        isLoading = true
        
        do {
            image = try await downloadImage(url: url)
            isLoading = false
        } catch {
            print("FetchImage Error:\(error.localizedDescription)")
            isLoading = false
        }
        
    }
    
    
    private func downloadImage(url: URL) async throws -> UIImage? {

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

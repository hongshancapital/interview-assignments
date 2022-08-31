//
//  ApplistRowViewModel.swift
//  AppMarket
//
//  Created by xcz on 2022/8/26.
//

import Foundation
import SwiftUI

class ApplistRowViewModel: ObservableObject {
    
    @Published var image: UIImage? = nil
    @Published var isLoading = false
    @Published var appInfo: AppInfoModel
    
    private let dataService = ApplistRowDataService()
    
    init(appInfo: AppInfoModel) {
        self.appInfo = appInfo
        Task {
            await fetchImage()
        }
    }
    
    
    @MainActor
    func fetchImage() async {

        isLoading = true
        
        do {
            image = try await dataService.downloadImage(url: URL(string: appInfo.artworkUrl100)!)
            isLoading = false
        } catch {
            print("FetchImage Error:\(error.localizedDescription)")
            isLoading = false
        }
        
    }
    
 
}

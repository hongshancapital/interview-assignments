//
//  MListCellViewModel.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import Foundation
import SwiftUI

class MListCellViewModel: ObservableObject {
    
    @Published var image: UIImage? = nil
    @Published var isLoading = false
    @Published var viewInfo: MViewInfo
    
    private var downloader: MImageDownloader? = nil
    
    init(viewInfo: MViewInfo) {
        self.viewInfo = viewInfo
        isLoading = true
        downloader = MImageDownloader(completion: { (image) in
            DispatchQueue.main.async {
                self.image = image
                self.isLoading = false
            }
        })
        if let url = URL(string: viewInfo.artworkUrl100) {
            downloader?.downloadImage(url: url)
        }
    }
}

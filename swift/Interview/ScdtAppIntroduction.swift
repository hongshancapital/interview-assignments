//
//  ScdtAppIntroduction.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/17.
//

import Foundation
import UIKit
import Combine

class ScdtAppIntroduction: Identifiable, ObservableObject {
    static func == (lhs: ScdtAppIntroduction, rhs: ScdtAppIntroduction) -> Bool {
        return lhs.trackId == rhs.trackId
    }
    
    @Published private(set) var iconImage: UIImage? = nil
    
    let trackId: String
    let name: String
    let introduction: String
    let imageUrl:String
    var like = false
    
    init(trackId: String, name: String, introduction: String, imageUrl: String) {
        self.trackId = trackId
        self.name = name
        self.introduction = introduction
        self.imageUrl = imageUrl
        
        Just(imageUrl)
            .subscribe(on: DispatchQueue.global())
            .tryMap { imageUrl -> UIImage? in
                guard let url = URL(string: imageUrl) else {
                    return nil
                }
                let data = try Data(contentsOf: url)
                return UIImage(data: data)
            }.receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion {
                case .failure(let error):
                    LogError(TAG, "failed to load image: error = %s", error.localizedDescription )
                default:
                    LogFinal(TAG, "load image success")
                }
            } receiveValue: { [weak self] image in
                self?.iconImage = image
            }
    }
}

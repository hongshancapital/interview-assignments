//
//  ImageViewModel.swift
//  assignment
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import Foundation
import UIKit
import Combine


class ImageViewModel: ObservableObject {
    @Published private(set) var image:UIImage?
    @Published private(set) var isLoading = false
    private var imageURL:String
    
    private var imageSubscription : AnyCancellable?
    init(imageURL: String){
        self.imageURL = imageURL
        self.isLoading = true
        getImage()
    }
    
    func getImage(){
        guard let url = URL(string: imageURL) else { return }
        
        self.imageSubscription = URLSession.shared.dataTaskPublisher(for: url)
            .subscribe(on: DispatchQueue.global(qos: .default))
            .tryMap { (data,response) -> Data in
                guard let response = response as? HTTPURLResponse, response.statusCode >= 200 && response.statusCode < 300 else {
                    throw NetWorkError.URLError
                }
                return data
            }
            .receive(on: DispatchQueue.main)
            .tryMap { data -> UIImage? in
                return UIImage(data: data)
            }
            .sink { completion in
                switch completion{
                case .finished:
                    break;
                case .failure(let error):
                    print(error.localizedDescription)
                }
            } receiveValue: { [weak self] returnedImage in
                self?.image = returnedImage
                self?.isLoading = false
                self?.imageSubscription?.cancel()
            }

    }
}

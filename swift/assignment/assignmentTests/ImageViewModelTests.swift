//
//  ImageViewModelTests.swift
//  assignmentTests
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import XCTest
@testable import assignment
import Combine

class ImageViewModelTests: XCTestCase {
    var imageURL: String = "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/60x.jpg"
    var imageVM: ImageViewModel?
    var imageSubscription: AnyCancellable?

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        imageVM = ImageViewModel(imageURL: self.imageURL)
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func test_ImageViewModel_isLoading_ShouldBeTrue(){
        if  let imageVM = self.imageVM{
            XCTAssertTrue(imageVM.isLoading)
        } else {
            XCTFail()
        }
    }
    
    func tests_ImageViewModel_getImage_ShouldSetImageCorrect(){
        var image: UIImage? = nil
        if let imageVM = imageVM {
            guard let url = URL(string: self.imageURL) else {
                return
            }
            XCTAssertNotNil(url)
            self.imageSubscription =  URLSession.shared.dataTaskPublisher(for: url)
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
                    image = returnedImage
                    XCTAssertNotNil(image)
                    XCTAssertEqual(image, imageVM.image)
                    self?.imageSubscription?.cancel()
                }
        }else{
            XCTFail()
        }
        
    }


}

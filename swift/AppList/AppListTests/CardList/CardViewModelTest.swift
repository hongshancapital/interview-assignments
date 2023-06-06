import XCTest
@testable import AppList

final class CardViewModelTest: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testInitWhenUnliked() throws {
        let viewModel = CardViewModel.notInUserDefaults
        XCTAssertFalse(viewModel.isLiked)
    }
    
    func testInitWhenLiked() throws {
        let bundleId = "bundleId"
        var likedData = UserDefaults.CardList.likedData.stringArrayValue
        if !(likedData?.contains(bundleId) ?? false) {
            likedData?.append(bundleId)
        }
        UserDefaults.CardList.likedData.set(likedData)
        let viewModel = CardViewModel.mockViewModel
        XCTAssertTrue(viewModel.isLiked)
    }
    
    func testClickLikeAction() throws {
        let viewModel = CardViewModel.click
        let isLiked = viewModel.isLiked
        viewModel.likeAction()
        XCTAssertEqual(isLiked, !viewModel.isLiked)
        viewModel.likeAction()
        XCTAssertEqual(isLiked, viewModel.isLiked)
    }
}

extension CardViewModel {
    static var mockViewModel: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "bundleId",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
    
    static var notInUserDefaults: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "notInUserDefaults",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
    
    static var click: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "oneClick",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
}

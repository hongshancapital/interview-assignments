import XCTest
@testable import AppList

final class CardViewModelTest: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testCheckLikeStateWhenNoDataInUserDefaults() throws {
        let viewModel = CardViewModel.notInUserDefaults
        viewModel.updateLikeState()
        XCTAssertFalse(viewModel.isLiked)
    }
    
    func testCheckLikeStateWhenDataInUserDefaults() throws {
        let viewModel = CardViewModel.mockViewModel
        viewModel.likeAction()
        viewModel.updateLikeState()
        XCTAssertTrue(viewModel.isLiked)
    }
    
    func testCheckLikeStateWithOneLikeAction() throws {
        let viewModel = CardViewModel.oneClick
        viewModel.likeAction()
        XCTAssertTrue(viewModel.isLiked)
    }
    
    func testCheckLikeStateWithTwoLikeAction() throws {
        let viewModel = CardViewModel.twiceClick
        viewModel.likeAction()
        viewModel.likeAction()
        XCTAssertFalse(viewModel.isLiked)
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
    
    static var oneClick: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "oneClick",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
    
    static var twiceClick: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "twiceClick",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
}

import XCTest
@testable import AppList

final class CardViewModelTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLikeAction() throws {
        let viewModel = CardViewModel.mockViewModel
        viewModel.likeAction()
        XCTAssertTrue(viewModel.isLiked)
    }

}

extension CardViewModel {
    static var mockViewModel: CardViewModel {
        CardViewModel(model: CardModel(bundleId: "mockViewModel",
                                       artistName: "name",
                                       description: "description",
                                       artworkUrl60: URL(string: "https://about:blank")!))
    }
}

